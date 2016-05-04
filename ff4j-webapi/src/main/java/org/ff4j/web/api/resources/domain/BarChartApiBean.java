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

import java.util.ArrayList;
import java.util.List;

import org.ff4j.audit.graph.BarChart;
import org.ff4j.audit.graph.BarSeries;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Representation of a barchart for the API.
 *
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
@ApiModel( value = "barChart", description = "resource representation of a bar chart" )
public class BarChartApiBean  {

    /** title of the graph. */
    @ApiModelProperty( value = "title of the graph", required = false )
    @JsonProperty("title")
    private String title = null;
    
    /** Labels on X axis. */
    @ApiModelProperty( value = "labels of the graph", required = false )
    @JsonProperty("labels")
    private List < String > labels = new ArrayList<String>();
    
    /** Data to be displayed. */
    @ApiModelProperty( value = "series of the graph", required = false )
    @JsonProperty("series")
    private List < BarSeriesApiBean > series = new ArrayList<BarSeriesApiBean>();
    
    /**
     * Copy constructor.
     *
     * @param barChart
     *      target barchart.
     */
    public BarChartApiBean(BarChart barChart) {
        this.title = barChart.getTitle();
        this.labels = barChart.getLabels();
        for(BarSeries bs : barChart.getSeries().values()) {
            series.add(new BarSeriesApiBean(bs));
        }
    }

    /**
     * Getter accessor for attribute 'title'.
     *
     * @return
     *       current value of 'title'
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter accessor for attribute 'title'.
     * @param title
     * 		new value for 'title '
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter accessor for attribute 'labels'.
     *
     * @return
     *       current value of 'labels'
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Setter accessor for attribute 'labels'.
     * @param labels
     * 		new value for 'labels '
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * Getter accessor for attribute 'series'.
     *
     * @return
     *       current value of 'series'
     */
    public List<BarSeriesApiBean> getSeries() {
        return series;
    }

    /**
     * Setter accessor for attribute 'series'.
     * @param series
     * 		new value for 'series '
     */
    public void setSeries(List<BarSeriesApiBean> series) {
        this.series = series;
    }
    
}
