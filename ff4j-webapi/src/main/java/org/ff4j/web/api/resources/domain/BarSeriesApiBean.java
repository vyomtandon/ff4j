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

import org.ff4j.audit.graph.BarSeries;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Target bean to display a bar series.
 *
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
@ApiModel( value = "barSeries", description = "resource representation of a bar series" )
public class BarSeriesApiBean {
    
    /** Label for this series. */
    @ApiModelProperty( value = "label for this sebar series", required = false )
    @JsonProperty("label")
    private String label = "N/A";
    
    /** color for this series. */
    @ApiModelProperty( value = "target color", required = false )
    @JsonProperty("color")
    private String color = "FFFFFF";
    
    /** value. */
    @ApiModelProperty( value = "target values", required = false )
    @JsonProperty("values")
    private List < Double > values = new ArrayList<Double>();
    
    /**
     * Constructor by copy.
     *
     * @param barSeries
     *      barSeries.
     */
    public BarSeriesApiBean(BarSeries barSeries) {
        this.label = barSeries.getLabel();
        this.color = barSeries.getColor();
        this.values = barSeries.getValues();
    }

    /**
     * Getter accessor for attribute 'label'.
     *
     * @return
     *       current value of 'label'
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter accessor for attribute 'label'.
     * @param label
     * 		new value for 'label '
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter accessor for attribute 'color'.
     *
     * @return
     *       current value of 'color'
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter accessor for attribute 'color'.
     * @param color
     * 		new value for 'color '
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Getter accessor for attribute 'values'.
     *
     * @return
     *       current value of 'values'
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * Setter accessor for attribute 'values'.
     * @param values
     * 		new value for 'values '
     */
    public void setValues(List<Double> values) {
        this.values = values;
    }

}
