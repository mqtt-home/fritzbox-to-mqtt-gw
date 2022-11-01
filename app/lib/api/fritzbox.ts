// @ts-ignore
import tr from "tr-064"

export type Options = {
    username: string
    password: string
    server: string
}

export const query = (service: string, method: string, options: Options): Promise<any> => {
    return new Promise((resolve, reject) => {
        const tr064 = new tr.TR064()
        tr064.initTR064Device(options.server, 49000, (err: any, device: any) => {
            if (!err) {
                device.startEncryptedCommunication((err: any, sslDev: any) => {
                    if (!err) {
                        sslDev.login([options.username], [options.password])
                        const svr = sslDev.services[`urn:dslforum-org:service:${service}`]
                        svr.actions[method]((err: any, result: any) => {
                            if (err) {
                                reject(err)
                            }
                            else {
                                resolve(result)
                            }
                        })
                    }
                    else {
                        reject(err)
                    }
                })
            }
            else {
                reject(err)
            }
        })
    })
}
