package com.anymetrik.opclogger.service;

import com.anymetrik.opclogger.model.Measure;
import com.anymetrik.opclogger.repository.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService {
    @Autowired
    private MeasureRepository measureRepository;

    public Measure addMeasure(Measure measure) {
        return measureRepository.save(measure);
    }
}
