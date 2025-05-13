package io.github.vishalmysore;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import lombok.extern.java.Log;

@Log
@Agent(groupName = "SuperemeHealthClub", groupDescription = "provide health club services")
public class SuperemeHealthClubService {

    @Action(description = "book an appointment for gym")
    public void bookAppointment(String appointmentDetails) {

        log.info("Gym appointment booked: " + appointmentDetails);
    }

    @Action(description = "book a squash court")
    public void bookSquashCourt(String courtDetails) {

        log.info("Squash court booked: " + courtDetails);
    }

    @Action(description = "cancel any type of appointment")
    public void cancelAppointment(String appointmentId, String type) {

        log.info("Appointment cancelled: " + appointmentId + " of type: " + type);
    }

    @Action(description = "reschedule an existing appointment")
    public void rescheduleAppointment(String appointmentId, String newDateTime) {

        log.info("Appointment rescheduled: " + appointmentId + " to " + newDateTime);
    }

    @Action(description = "book a swimming session")
    public void bookSwimmingSession(String sessionDetails) {

        log.info("Swimming session booked: " + sessionDetails);
    }

    @Action(description = "register for fitness class")
    public void registerForClass(String className, String scheduleTime) {

        log.info("Registered for class: " + className + " at " + scheduleTime);
    }

}
