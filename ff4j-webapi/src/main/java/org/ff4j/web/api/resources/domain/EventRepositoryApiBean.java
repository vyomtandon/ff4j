package org.ff4j.web.api.resources.domain;

/*
 * #%L
 * ff4j-web
 * %%
 * Copyright (C) 2013 - 2014 Ff4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Calendar;
import java.util.Date;

import org.ff4j.audit.graph.BarChart;
import org.ff4j.audit.graph.PieChart;
import org.ff4j.audit.graph.PieSector;
import org.ff4j.audit.repository.EventRepository;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Bean to represent the event repository.
 *
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
@ApiModel( value = "eventRepositoryApiBean", description = "resource representation of monitoring resources" )
public class EventRepositoryApiBean {
    
    /** ClassName of the event repository. */
    @ApiModelProperty( value = "type of the repository", required = true )
    @JsonProperty("type")
    private String type;
    
    /** total hit count. */
    @ApiModelProperty( value = "total hit of the monitoring", required = true )
    @JsonProperty("hitCount")
    private int hitCount = 0;
    
    /** Hit Pie. */
    @ApiModelProperty( value = "pie of features", required = true )
    @JsonProperty("eventsPie")
    private PieChartApiBean eventsPie;
    
    /** Bar Chart. */
    @ApiModelProperty( value = "barChart for activity", required = true )
    @JsonProperty("barChart")
    private BarChartApiBean barChart;
    
    /**
     * Default constructor.
     */
    public EventRepositoryApiBean() {
    }
            
    /**
     * Constructor to get API bean.
     *
     * @param evtRepository
     *      target event repository
     */
    public EventRepositoryApiBean(EventRepository evtRepository, Long start, Long end) {
        type = evtRepository.getClass().getCanonicalName();
        // Today
        if (start == null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            start = c.getTimeInMillis();
        }
        // Tomorrow 00:00
        if (end == null) {
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
            c2.set(Calendar.HOUR_OF_DAY, 0);
            c2.set(Calendar.MINUTE, 0);
            c2.set(Calendar.SECOND, 0);
            end = c2.getTimeInMillis();
        }
        // Create PIE
        PieChart pie = evtRepository.featuresListDistributionPie(start, end);
        eventsPie = new PieChartApiBean(pie);
        // Create BARCHART
        BarChart bc = evtRepository.getFeaturesUsageOverTime(start, end, 24);
        barChart = new BarChartApiBean(bc);
        // Total Count
        for(PieSector sector : pie.getSectors()) {
            hitCount += sector.getValue();
        }
    }

    /**
     * Getter accessor for attribute 'type'.
     *
     * @return
     *       current value of 'type'
     */
    public String getType() {
        return type;
    }

    /**
     * Setter accessor for attribute 'type'.
     * @param type
     * 		new value for 'type '
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter accessor for attribute 'hitCount'.
     *
     * @return
     *       current value of 'hitCount'
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * Setter accessor for attribute 'hitCount'.
     * @param hitCount
     * 		new value for 'hitCount '
     */
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    /**
     * Getter accessor for attribute 'eventsPie'.
     *
     * @return
     *       current value of 'eventsPie'
     */
    public PieChartApiBean getEventsPie() {
        return eventsPie;
    }

    /**
     * Setter accessor for attribute 'eventsPie'.
     * @param eventsPie
     * 		new value for 'eventsPie '
     */
    public void setEventsPie(PieChartApiBean eventsPie) {
        this.eventsPie = eventsPie;
    }

    /**
     * Getter accessor for attribute 'barChart'.
     *
     * @return
     *       current value of 'barChart'
     */
    public BarChartApiBean getBarChart() {
        return barChart;
    }

    /**
     * Setter accessor for attribute 'barChart'.
     * @param barChart
     * 		new value for 'barChart '
     */
    public void setBarChart(BarChartApiBean barChart) {
        this.barChart = barChart;
    }

}
