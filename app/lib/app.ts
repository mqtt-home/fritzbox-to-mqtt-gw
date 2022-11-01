import { getAppConfig } from "./config/config"
import { Options, query } from "./api/fritzbox"
import { log } from "./logger"
import { connectMqtt, publish } from "./mqtt/mqtt-client"

const isNumeric = (str: any) => {
    return !isNaN(parseFloat(str)) && isFinite(str)
}

const convertType = (value: any) => {
    return isNumeric(value) ? +value : value
}

const fetchFritzBoxData = async (config = getAppConfig().fritzbox) => {
    const options: Options = {
        server: config.host,
        username: config.username,
        password: config.password
    }

    const result: any = {}

    for (const message of config.message) {
        try {
            const data = await query(message.service, message.action, options)

            for (const value of message.values) {
                let valueData = data[value.name]
                if (value.mapEnum) {
                    const key = Object.keys(value.mapEnum)
                        .filter(key => key.toUpperCase() === valueData.toUpperCase())[0]

                    if (key) {
                        valueData = value.mapEnum[key]
                    }
                    else {
                        valueData = value.mapEnum.__default
                    }
                }

                result[value.name] = convertType(valueData)
            }
        }
        catch (e: any) {
            log.error(`Error while fetching data from FritzBox (${message.service}, ${message.action}) ${e.message}`)
        }
    }

    return result
}

const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms))

export const startApp = async () => {
    await connectMqtt()

    const app = async () => {
        const interval = getAppConfig().fritzbox["polling-interval"]

        while (true) {
            const data = await fetchFritzBoxData()

            publish(data, getAppConfig().fritzbox["box-type"])
            await sleep(interval * 1000)
        }
    }

    app().then()
}
