SFDC-Camel
==========
This project is about integration of Contract information with SFDC [Salesforce].

The base scenario is like the following:
- Emptoris push message to an external message System: SAP PI
- A bridge written in Camel bridge the messages from a SAP PI topic to ActiveMQ vitual Topic
- The message on the virtual topic is bridged again to an ActiveMQ queue
- A camel route consumes the message from the queue
- A processor process the message
  + if the message is for the targeted SFDC, 
  +   build soap mesage and send the message to the SFDC
  +      if a paritcular error occurs,
  +          send email, so that SFDC admin can correct the data
  + Otherwise
  +   skip the message
