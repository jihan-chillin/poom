'use strict';
function getContextPath(){
  let hostIndex = location.href.indexOf( location.host ) + location.host.length;
  let contextPath = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
  return contextPath;
}