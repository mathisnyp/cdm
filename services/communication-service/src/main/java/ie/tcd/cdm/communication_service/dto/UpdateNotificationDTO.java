package ie.tcd.cdm.communication_service.dto;


import java.sql.Timestamp;

public record UpdateNotificationDTO(

        String messageBody,

        String messageHeader,

        int messageSender,

        int receiver,

        String receiverGroup,

        Timestamp scheduledTime
){}
