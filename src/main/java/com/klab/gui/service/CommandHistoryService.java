package com.klab.gui.service;

import com.klab.gui.model.Command;
import com.klab.gui.model.CommandHistoryIterator;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommandHistoryService {
    void add(String command);

    Map<Date, List<Command>> getAllByDay();

    CommandHistoryIterator iterator();
}
