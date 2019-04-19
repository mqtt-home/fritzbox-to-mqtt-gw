package de.rnd7.speedportmqttgw.speedport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Speedport {
	private final String sessionId;
	private final String url;

	public Speedport(final String url, final String password) throws IOException {
		this.url = url;
		final String challenge = this.fetchChallenge(url);
		this.sessionId = this.login(url, challenge, password);
	}

	public JSONObject downloadInfo(final String data) throws IOException {
		try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
			final HttpGet httpGet = new HttpGet(String.format("%s/data/%s.json", this.url, data));

			httpGet.addHeader("Cookie", String.format("challengev=; %s", this.sessionId));

			try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
				return new JSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
						.replaceAll("'(-?\\d+)'", "$1").replace("'", "\""));
			}
		}
	}

	private String fetchChallenge(final String speedPortIp) throws IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse response = httpclient.execute(new HttpGet(this.indexPage(speedPortIp)))) {
			final String page = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			final Pattern pattern = Pattern.compile("[0-9,a-z,A-Z]{64}");
			final Matcher matcher = pattern.matcher(page);

			if (matcher.find()) {
				return matcher.group();
			}

			throw new IOException("cannot find challenge on index page");
		}
	}

	@SuppressWarnings("squid:S2068")
	private String login(final String speedPortIp, final String challenge, final String password) throws IOException {
		final String token = challenge + ":" + password;

		try {

			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] data = digest.digest(token.getBytes(StandardCharsets.UTF_8));

			final String request = String.format("password=%s&csrf_token=nulltoken&showpw=0&challengev=%s",
					byteArrayToHex(data), challenge);

			final HttpPost post = new HttpPost(String.format("%s/data/Login.json", speedPortIp));
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setHeader("X-Requested-With", "XMLHttpRequest");
			post.setHeader("Referer", this.indexPage(speedPortIp));
			post.setEntity(new StringEntity(request));

			try (CloseableHttpClient httpclient = HttpClients.createDefault();
					CloseableHttpResponse response = httpclient.execute(post)) {

				final Pattern pattern = Pattern.compile("^.*(SessionID_R3=[a-zA-Z0-9]*)");

				final Matcher matcher = pattern.matcher(Arrays.toString(response.getHeaders("Set-Cookie")));
				if (matcher.find()) {
					return matcher.group(1);
				}

				throw new IOException("unable to obtain session id");
			}
		} catch (final NoSuchAlgorithmException e) {
			throw new IOException(e);
		}
	}

	public static String byteArrayToHex(final byte[] a) {
		final StringBuilder sb = new StringBuilder(a.length * 2);
		for (final byte b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	private String indexPage(final String speedPortIp) {
		return String.format("%s/html/login/index.html", speedPortIp);
	}
}
