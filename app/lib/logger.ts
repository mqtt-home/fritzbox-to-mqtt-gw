import * as winston from "winston"
import * as Transport from "winston-transport"
import { LoggerOptions } from "winston"
import chalk from "chalk"

export const logLevelColor = (level: string) => {
    const lvl = level.toUpperCase()
    switch (lvl) {
    case "FATAL":
    case "ERROR":
        return chalk.red(lvl)
    case "WARN":
        return chalk.yellow(lvl)
    case "INFO":
        return chalk.blue(lvl)
    case "DEBUG":
    case "TRACE":
        return chalk.magenta(lvl)
    default:
        return lvl
    }
}

const transformError = (data: any) => {
    if (data instanceof Error) {
        data = {
            error: {
                name: data.name,
                message: data.message,
                stack: data.stack
            }
        }
    }
    return data
}

export const unpackError = (data: any): any => {
    if (data) {
        if (Array.isArray(data)) {
            if (data.length === 0) {
                return null
            }
            for (let i = 0; i < data.length; i++) {
                data[i] = transformError(data[i])
            }

            if (data.length === 1) {
                return data[0]
            }
        }
        else if (data instanceof Error) {
            data = transformError(data)
        }
    }
    return data
}

function toISOString (date = new Date()) {
    const tzo = -date.getTimezoneOffset()
    const dif = tzo >= 0 ? "+" : "-"
    const pad = (num: number) => {
        return (num < 10 ? "0" : "") + num
    }

    return date.getFullYear() +
        "-" + pad(date.getMonth() + 1) +
        "-" + pad(date.getDate()) +
        "T" + pad(date.getHours()) +
        ":" + pad(date.getMinutes()) +
        ":" + pad(date.getSeconds()) +
        dif + pad(Math.floor(Math.abs(tzo) / 60)) +
        ":" + pad(Math.abs(tzo) % 60)
}

const txtFormat = () => {
    return winston.format
        .printf(event => {
            let data = unpackError(event.data)

            if (data && typeof data !== "string") {
                data = JSON.stringify(data)
            }

            return `${toISOString()} [${logLevelColor(event.level)}] ${event.message} ${chalk.gray(data || "")}`
                .trim()
        })
}

export const loggerConfig = (loglevel: string, transport: Transport = new winston.transports.Console()) => {
    return {
        level: loglevel,
        levels: {
            FATAL: 100,
            ERROR: 200,
            WARN: 300,
            INFO: 400,
            DEBUG: 500,
            TRACE: 600
        },
        format: txtFormat(),
        transports: [
            transport
        ]
    } as LoggerOptions
}

export const createLogger = (transport: Transport = new winston.transports.Console()) => {
    return winston.createLogger(loggerConfig("INFO", transport))
}

let logger = createLogger()

export const setLogger = (newLogger: winston.Logger) => {
    logger = newLogger
}

export const log = {
    fatal: (message: string, ...data: any) => logger.log({ level: "FATAL", message, data }),
    error: (message: string, ...data: any) => logger.log({ level: "ERROR", message, data }),
    warn: (message: string, ...data: any) => logger.log({ level: "WARN", message, data }),
    info: (message: string, ...data: any) => logger.log({ level: "INFO", message, data }),
    debug: (message: string, ...data: any) => logger.log({ level: "DEBUG", message, data }),
    trace: (message: string, ...data: any) => logger.log({ level: "TRACE", message, data }),

    off: () => {
        logger.silent = true
    },
    on: () => {
        logger.silent = false
    },
    configure: (loglevel: string) => logger.configure(loggerConfig(loglevel))
}
