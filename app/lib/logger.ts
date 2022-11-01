import * as winston from "winston"
import * as Transport from "winston-transport"

export const createLogger = (transport: Transport = new winston.transports.Console()) => {
    return winston.createLogger({
        level: "info",
        format: winston.format.printf(event => `${new Date().toISOString()} ${event.level.toUpperCase()} ${event.message}`),
        transports: [
            transport
        ]
    })
}

export const log = createLogger()
