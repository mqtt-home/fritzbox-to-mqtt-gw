# Build the application from source
FROM golang:1.23 AS build-stage

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY . ./

RUN CGO_ENABLED=0 GOOS=linux go build -o /fritzbox-to-mqtt-gw

# Run the tests in the container
FROM build-stage AS run-test-stage
RUN go test -v ./...

# Deploy the application binary into a lean image
FROM gcr.io/distroless/base-debian11 AS build-release-stage

WORKDIR /

COPY --from=build-stage /fritzbox-to-mqtt-gw /fritzbox-to-mqtt-gw

USER nonroot:nonroot

ENTRYPOINT ["/fritzbox-to-mqtt-gw", "/var/lib/fritzbox-to-mqtt-gw/config.json"]
