export interface MockUser {
    id: string,
    username: string,
    email: string
}

const mockUser: MockUser = {
    id: "someId",
    username: "uadaw",
    email: "uadaw@tcd.ie"
}

export const cdmMockAuth = {
    account: {
        get: async () => {
            return mockUser
        },
        createEmailSession: async (email: string, password: string) => {
            return true
        },
        deleteSession: async (sessionName: string) => {
            return true;
        },
        create: async (id: string, email: string, password: string, username: string) => {
            return true
        }
    },
    ID: {
      unique: () => {
          return crypto.randomUUID()
      }
    }
}
