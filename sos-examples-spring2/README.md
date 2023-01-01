# Furnace system 
### Business Rationale
 Furnace is a device to craft products by melting, smelting different materials. 
 To craft products desired temperature has to be reached inside the furnace, and thus there is the heating system that ultimately controls temperature of the furnace. 


The heating system receives temperature measurements from the temperature sensors and based on previously defined expected temperature control heaters.
The heater consumes information from heating system to heat up or cool down the furnace. This translates to turn off/on the heater. 


The furnace consists of following systems working altogether in local network
- heating system
  - turns off/on the furnance
  - sets desired temperature 
  - consumer receives temperature measurements
  - provider sends commands to heat up or cool down
- heater consumer turns on/off heaters based on received command  
- temperature sensor provider sends temperature measurements 
- door sensor provider sends door status [open, closed]

#### Prerequisites

1. Arrowhead core systems up and running in local environment in secure mode
   - authorization system
   - orchestrator
   - service registry
2. Execute `mvn install` in root project
3. Go to `furnace/scripts/certs` and generate certificates for all services using script `mk_certs_heatingsystem.sh`
4. Install `sysop` certificate, so you can access core systems in web browser
5. Temperature Sensor Provider 
   5.1. Run temperature sensor provider, it will register to service registry itself 
   5.2. GET /serviceregistry/mgnt/systems check if provider is registered
6. Door Status Sensor Provider
   6.1. Run temperature sensor provider, it will register to service registry itself
   6.2. GET /serviceregistry/mgnt/systems check if provider is registered
7. Heating System 
   7.1. POST /serviceregistry/mgnt/systems register consumer as heating system
   7.2. Run Heating system, it will register its provider
   7.3  POST /authorization/mgnt/intracloud register authorization rule between heating system(consumer) and sensor provider
8. Heater Consumer
   8.1. POST /serviceregistry/mgnt/systems register consumer as heating system
   8.2. Run heater consumer
   8.3  POST /authorization/mgnt/intracloud register authorization rule between heater consumer and heating system(provider)

#### Execution
In prerequisites, you should have all services running. 
You can turn on/off furnace by heating system calling POST /on or POST /off 
Additionally the furnace will shut down automatically whenever you will try open the door
Set temperature by calling heating system thermostat/temperature/{celsius}

Observe logs to see changing temperature in `environment.json`