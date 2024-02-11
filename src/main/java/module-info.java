/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  module-info.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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
module esialrobotik.ia {
    // Pi4J MODULES
    requires com.pi4j;
    requires com.pi4j.plugin.pigpio;

    // SLF4J MODULES
    requires org.slf4j;
    requires org.slf4j.simple;

    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // GSon
    requires com.google.gson;

    // Log4J
    requires org.apache.logging.log4j;

    requires java.sql;
    uses java.sql.Timestamp;
    requires org.java_websocket;
    requires jdk.httpserver;
    requires picam;
    requires rxtx;
    requires java.desktop;

    // allow access to classes in the following namespaces for Pi4J annotation processing
    opens esialrobotik.ia.api.gpio to com.pi4j;
    opens esialrobotik.ia.api.camera to com.pi4j;
    opens esialrobotik.ia.api.log to com.pi4j;
    opens esialrobotik.ia.core to com.pi4j;
}