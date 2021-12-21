package com.lamzone.mareu.di;

import com.lamzone.mareu.service.MeetingApiService;
import com.lamzone.mareu.service.MeetingApiServiceInterface;

public class DI {

    private static MeetingApiServiceInterface service = new MeetingApiService();

    public static MeetingApiServiceInterface getMeetingApiService() { return service; }

    public static MeetingApiServiceInterface getNewInstanceApiService() {
        return new MeetingApiService();
    }

}
