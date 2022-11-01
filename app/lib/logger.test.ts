import { log } from "./logger"
import { Writable } from "stream"
import * as winston from "winston"
import * as Transport from "winston-transport"

export class TestLogger {
    output = ""
    transports: Transport[]

    constructor () {
        const stream = new Writable()
        stream._write = (chunk, encoding, next) => {
            this.output = this.output += chunk.toString()
            next()
        }
        this.transports = [...log.transports]
        log.clear()
        log.add(new winston.transports.Stream({ stream }))
    }

    close = () => {
        log.clear()
        for (const transport of this.transports) {
            log.add(transport)
        }
    }
}

describe("Log format", () => {
    let logger: TestLogger

    beforeAll(() => {
        logger = new TestLogger()
    })

    afterEach(() => {
        logger.output = ""
    })

    test("info log", () => {
        log.info("some info")

        expect(logger.output).toMatch(/\d+\d+\d+\d+-\d+\d+-\d+\d+T.* INFO some info.*/)
    })

    test("warn log", () => {
        log.warn("some warning")

        expect(logger.output).toMatch(/\d+\d+\d+\d+-\d+\d+-\d+\d+T.* WARN some warning.*/)
    })

    test("error log", () => {
        log.error("some error")

        expect(logger.output).toMatch(/\d+\d+\d+\d+-\d+\d+-\d+\d+T.* ERROR some error.*/)
    })
})
