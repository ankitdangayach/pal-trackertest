package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{
    HashMap<Long,TimeEntry> dataMap = new HashMap<>();
    private long  sequenceNumber=0L;

    public TimeEntry create(TimeEntry timeEntry) {
        sequenceNumber++;
        timeEntry.setId(sequenceNumber);
        dataMap.put(sequenceNumber,timeEntry);
        return timeEntry;
    }

    public TimeEntry find(Long id) {
        TimeEntry readEntry= dataMap.get(id);
        if(readEntry==null){
            return null;
        }
        else {
            return dataMap.get(id);
        }
    }
    

    public TimeEntry update(Long id, TimeEntry timeEntry) {
        TimeEntry te = dataMap.get(id);
        if(null==te){
            return null;
        }
        else {
            te.setUserId(timeEntry.getUserId());
            te.setProjectId(timeEntry.getProjectId());
            te.setHours(timeEntry.getHours());
            te.setDate(timeEntry.getDate());
            return te;
        }
    }

    public TimeEntry delete(Long id) {
        dataMap.remove(id);
        return null;
    }

    public List<TimeEntry> list() {

        return new ArrayList(dataMap.values());
    }
}
