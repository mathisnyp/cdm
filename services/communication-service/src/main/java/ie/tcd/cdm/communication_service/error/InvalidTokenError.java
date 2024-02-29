package ie.tcd.cdm.communication_service.error;

import java.util.List;

public class InvalidTokenError extends Error{
    public InvalidTokenError(List<String> tokens) {
        super("Token:" + tokens + " is not a valid token.");
    }
}
