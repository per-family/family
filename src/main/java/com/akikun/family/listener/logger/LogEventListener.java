package com.akikun.family.listener.logger;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.akikun.family.event.logger.LogEvent;
import com.akikun.family.model.entity.Log;
import com.akikun.family.service.LogService;

/**
 * Log event listener.
 *
 * @author johnniang
 * @date 19-4-21
 */
@Component
public class LogEventListener {

    private final LogService logService;

    public LogEventListener(LogService logService) {
        this.logService = logService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(LogEvent event) {
        // Convert to log
        Log logToCreate = event.getLogParam().convertTo();

        // Create log
        logService.create(logToCreate);
    }
}
