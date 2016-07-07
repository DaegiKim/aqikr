var mapContainer = document.getElementById('map');
var mapOption = {
    center: new daum.maps.LatLng(37.5665, 126.9780),
    level: 9
};
var map = new daum.maps.Map(mapContainer, mapOption);
var zoomControl = new daum.maps.ZoomControl();
map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
map.setZoomable(false);

function renderData(data, mode) {
    jQuery.each(data, function(k, station) {
        var polygonPath = [];
        var color, aqi;

        if(mode==='PM10') {
            color = station.pm10.color;
            aqi = station.pm10.aqi;
        } else if(mode==='PM25') {
            color = station.pm25.color;
            aqi = station.pm25.aqi;
        }

        jQuery.each(station.polygon, function(k, latlng) {
            polygonPath.push(new daum.maps.LatLng(latlng.lat, latlng.lng));
        });

        var polygon = new daum.maps.Polygon({
            path:polygonPath,
            strokeWeight: 1,
            strokeColor: '#fff',
            strokeOpacity: 1.0,
            strokeStyle: 'solid',
            fillColor: color,
            fillOpacity: 0.5
        });

        polygon.setMap(map);

        // daum.maps.event.addListener(polygon, 'mouseover', function() {
        //     alert('test');
        // });

        var customOverlay = new daum.maps.CustomOverlay({
            position: new daum.maps.LatLng(station.center.lat, station.center.lng),
            content: '<div class ="label">'+aqi+'</div>'
        });

        customOverlay.setMap(map);
    });
}