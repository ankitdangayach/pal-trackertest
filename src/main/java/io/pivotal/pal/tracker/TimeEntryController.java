package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;
    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary=meterRegistry.summary("timeEntry.summary");
        actionCounter=meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry=timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(timeEntry, HttpStatus.CREATED);
    }
    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntryFind=timeEntryRepository.find(id);
        if(timeEntryFind==null){
            return new ResponseEntity(timeEntryFind, HttpStatus.NOT_FOUND);
        }
        else {
            actionCounter.increment();
            return new ResponseEntity(timeEntryFind, HttpStatus.OK);

        }    }
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry timeEntryUpdate=timeEntryRepository.update(id,timeEntryToUpdate);
        if(timeEntryUpdate==null){
            return new ResponseEntity(timeEntryUpdate, HttpStatus.NOT_FOUND);
        }
        else {
            actionCounter.increment();
            return new ResponseEntity(timeEntryUpdate, HttpStatus.OK);
        }
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        /*if(timeEntryDelete==null) {
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity(id, HttpStatus.NOT_FOUND);
        }*/
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
