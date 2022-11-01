import * as fs from "fs"

export type ConfigMqtt = {
    url: string,
    topic: string
    username?: string
    password?: string
    retain: boolean
    qos: (0|1|2)
    "bridge-info"?: boolean
    "bridge-info-topic"?: string
}

export type Value = {
    name: string
    mapEnum?: any
}

export type MessageFragment = {
    service: string
    action: string
    values: Value[]
}

export type ConfigFritzBox = {
    "polling-interval": number
    "host": string
    "username": string
    "password": string
    "box-type": string,
    "message": MessageFragment[]
}

export type Config = {
    mqtt: ConfigMqtt
    fritzbox: ConfigFritzBox
}

let appConfig: Config

const mqttDefaults = {
    qos: 1,
    retain: true,
    "bridge-info": true
}

const fritzboxDefaults = {
    "polling-interval": 60
}

export const applyDefaults = (config: any) => {
    return {
        ...config,
        mqtt: { ...mqttDefaults, ...config.mqtt },
        fritzbox: { ...fritzboxDefaults, ...config.fritzbox }
    } as Config
}

export const loadConfig = (file: string) => {
    const buffer = fs.readFileSync(file)
    applyConfig(JSON.parse(buffer.toString()))
}

export const applyConfig = (config: any) => {
    appConfig = applyDefaults(config)
}

export const getAppConfig = () => {
    return appConfig
}

export const setTestConfig = (config: Config) => {
    appConfig = config
}
