var mapContainer = document.getElementById('map');
var mapOption = {
    center: new daum.maps.LatLng(37.5665, 126.9780),
    level: 9
};
var map = new daum.maps.Map(mapContainer, mapOption);
var zoomControl = new daum.maps.ZoomControl();
map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
map.setZoomable(false);

function renderData(data) {
    jQuery.each(data, function(k, station) {
        var polygonPath = [];
        var color, aqi, name = k;

        color = station.color;
        aqi = station.pm25.aqi;

        // var aqiLabel = new daum.maps.CustomOverlay({
        //     position: new daum.maps.LatLng(station.center.lat, station.center.lng),
        //     content: '<div class ="label">'+aqi+'</div>'
        // });
        //
        // aqiLabel.setMap(map);

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

        daum.maps.event.addListener(polygon, 'click', function () {
            jQuery("#aqi-card").show();

            jQuery("#aqi-card .station-name").html("<a href='"+station.link+"'>"+name+"</a>");

            jQuery("#aqi-card .main-data .aqi").text(station.pm25.aqi);
            jQuery("#aqi-card .main-data .text").text(station.pm25.label)
                .removeClass(function (index, css) {
                    return (css.match (/(^|\s)level-\S+/g) || []).join(' ');
                })
                .addClass("aqi-label "+ station.pm25.level);

            jQuery("#aqi-card .data-list .pm25 .aqi").text(station.pm25.aqi);
            jQuery("#aqi-card .data-list .pm25 .raw .value").text(station.pm25.raw);
            jQuery("#aqi-card .data-list .pm25 .text").text(station.pm25.label)
                .removeClass(function (index, css) {
                    return (css.match (/(^|\s)level-\S+/g) || []).join(' ');
                })
                .addClass("aqi-label "+ station.pm25.level);

            jQuery("#aqi-card .data-list .pm10 .aqi").text(station.pm10.aqi);
            jQuery("#aqi-card .data-list .pm10 .raw .value").text(station.pm10.raw);
            jQuery("#aqi-card .data-list .pm10 .text").text(station.pm10.label)
                .removeClass(function (index, css) {
                    return (css.match (/(^|\s)level-\S+/g) || []).join(' ');
                })
                .addClass("aqi-label " + station.pm10.level);

            jQuery("#aqi-card .timestamp .value").text(station.datetime);
        });

        daum.maps.event.addListener(polygon, 'mouseover', function () {
            polygon.setOptions({
                fillOpacity: 0.75
            });
        });

        daum.maps.event.addListener(polygon, 'mouseout', function () {
            polygon.setOptions({
                fillOpacity: 0.5
            });
        });
    });
}

jQuery(document).ready(function() {
    jQuery(document).on("click","#aqi-card .aqi-card-close",function() {
        jQuery("#aqi-card").hide();
    });
});