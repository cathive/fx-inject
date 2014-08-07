/*
 * Copyright (C) 2013,2014 The Cat Hive Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cathive.fx.cdi.weld;

import com.cathive.fx.cdi.spi.CDILoader;
import org.jboss.weld.environment.se.Weld;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CDI loader implementation based on JBoss WELD.
 * @author Benjamin P. Jung
 */
public class WeldLoader implements CDILoader {

    /** Logger for this instance. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * JBoss WELD instance to be used for all CDI-related operations.
     */
    private Weld weld;

    @Override
    public void initialize() throws Exception {
        logger.log(Level.INFO, "Initializing JBoss WELD...");
        this.weld = new Weld();
        this.weld.initialize();
    }

    @Override
    public void shutdown() throws Exception {
        if (this.weld != null) {
            logger.log(Level.INFO, "Shutting down JBoss WELD...");
            this.weld.shutdown();
        }
    }

}
