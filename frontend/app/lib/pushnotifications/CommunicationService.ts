export class CommunicationService {
    private static url = "http://api-gateway.project-unified.eu/communication-service/device"

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
