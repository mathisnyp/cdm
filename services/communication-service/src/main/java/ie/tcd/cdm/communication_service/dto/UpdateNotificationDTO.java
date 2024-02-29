package ie.tcd.cdm.communication_service.dto;


import java.sql.Timestamp;

public record UpdateNotificationDTO(

        String messageBody,

        String messageHeader,

        long messageSender,

        long receiver,

        String receiverGroup,

        Timestamp scheduledTime
){}
