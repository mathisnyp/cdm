export class CommunicationService {
    private static url = "localhost:8092/communication-service/device"

    public static async registerPushToken(requestBody: RegisterDeviceDTO) {
        await fetch(this.url + "/register", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        })
    }
}
