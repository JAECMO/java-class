$(document).ready(function () {
    getCurrentWeather();
});

function getCurrentWeather() {
    $('#getWeatherButton').click(function (event) {
        $('.errorMessages').empty();
        var haveValidationErrors = checkAndDisplayValidationErrors($('#headerDiv').find('input'));

        if (haveValidationErrors) {
            return false;
        }

        $.ajax({
            type: 'GET',
            url: 'https://api.openweathermap.org/data/2.5/weather?zip=' + $('#zipCode').val() + '&appid=a4033c8dd431fc740a4ffdc3898d0d9c&units=' + $('#unitsSelection').val(),
            success: function (data, status) {
                var unitTemp;
                var unitSpeed;
                if ($('#unitsSelection').val() === 'imperial') {
                    unitTemp = 'F';
                    unitSpeed = 'miles/hour';
                } else {
                    unitTemp = 'C';
                    unitSpeed = 'meter/sec';
                }
                $('#currentCity').text('Current conditions in ' + data.name);
                $('#currentIcon').attr('src', 'http://openweathermap.org/img/w/' + data.weather[0].icon + '.png');
                $('#currentMain').text(data.weather[0].main + ':');
                $('#currentDesc').text(data.weather[0].description);
                $('#currentTemp').text('Temperature: ' + data.main.temp + ' ' + unitTemp);
                $('#currentHum').text('Humidity: ' + data.main.humidity + ' %');
                $('#currentWind').text('Wind: ' + data.wind.speed + ' ' + unitSpeed);
                $('#currentConditionsDiv').show();
                getForecastWeather(data); 

            },
            'error': function (jqXHR) {
                if (jqXHR.status === 404) {
                    $('.errorMessages')
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger' })
                        .text('Zipcode invalid'))
                } else {
                    // Handle error
                    $('.errorMessages')
                        .append($('<li>')
                            .attr({ class: 'list-group-item list-group-item-danger' })
                            .text('Error calling web service. Please try again later.'));
                }
                $('#currentConditionsDiv').hide();
                $('#forecastDiv').hide();
            }
        })

        
    })
}

function getForecastWeather(data){

    $.ajax({
        type: 'GET',
        url: 'https://api.openweathermap.org/data/2.5/forecast?lat='+data.coord.lat+'&lon='+data.coord.lon+'&appid=a4033c8dd431fc740a4ffdc3898d0d9c&units='+ $('#unitsSelection').val(),
        success: function (data, status) {
           var unitTemp;
            if ($('#unitsSelection').val() === 'imperial') {
                unitTemp = 'F';
            } else {
                unitTemp = 'C';
            }
            $('#day1Date').text(formatDate(data.list[0].dt_txt));//start at 9pm
            $('#day1Icon').attr('src', 'http://openweathermap.org/img/w/' + data.list[0].weather[0].icon + '.png')
            $('#day1Main').text(data.list[0].weather[0].main);
            $('#day1Temp').text('H ' + data.list[0].main.temp_max +' '+ unitTemp+' ' + 'L ' + data.list[0].main.temp_min +' ' + unitTemp);

            $('#day2Date').text(formatDate(data.list[8].dt_txt));// noon
            $('#day2Icon').attr('src', 'http://openweathermap.org/img/w/' + data.list[8].weather[0].icon + '.png')
            $('#day2Main').text(data.list[8].weather[0].main);
            $('#day2Temp').text('H ' + data.list[8].main.temp_max +' '+ unitTemp+' ' + 'L ' + data.list[8].main.temp_min +' ' + unitTemp);

            $('#day3Date').text(formatDate(data.list[16].dt_txt));
            $('#day3Icon').attr('src', 'http://openweathermap.org/img/w/' + data.list[16].weather[0].icon + '.png')
            $('#day3Main').text(data.list[16].weather[0].main);
            $('#day3Temp').text('H ' + data.list[16].main.temp_max +' '+ unitTemp+' ' + 'L ' + data.list[16].main.temp_min +' ' + unitTemp);

            $('#day4Date').text(formatDate(data.list[23].dt_txt));
            $('#day4Icon').attr('src', 'http://openweathermap.org/img/w/' + data.list[23].weather[0].icon + '.png')
            $('#day4Main').text(data.list[23].weather[0].main);
            $('#day4Temp').text('H ' + data.list[23].main.temp_max +' '+ unitTemp+' ' + 'L ' + data.list[23].main.temp_min +' ' + unitTemp);

            $('#day5Date').text(formatDate(data.list[31].dt_txt));
            $('#day5Icon').attr('src', 'http://openweathermap.org/img/w/' + data.list[31].weather[0].icon + '.png')
            $('#day5Main').text(data.list[31].weather[0].main);
            $('#day5Temp').text('H ' + data.list[31].main.temp_max +' '+ unitTemp+' ' + 'L ' + data.list[31].main.temp_min +' ' + unitTemp);
            
            
            $('#forecastDiv').show();

         

        },
        'error': function (jqXHR) {
            if (jqXHR.status === 404) {
                $('.errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Zipcode invalid'))
            } else {
                // Handle error
                $('.errorMessages')
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger' })
                        .text('Error calling web service. Please try again later.'));
            }
            $('#currentConditionsDiv').hide();
                $('#forecastDiv').hide();
        }
    })



}


function formatDate(inputDate) {
  const monthNames = [
    "January", "February", "March",
    "April", "May", "June", "July",
    "August", "September", "October",
    "November", "December"
  ];

  
  const dateParts = inputDate.split(" ")[0].split("-");
  const year = dateParts[0];
  const monthIndex = parseInt(dateParts[1]) - 1; // Subtract 1 to get the correct month index
  const day =parseInt(dateParts[2]);

  const formattedDate = day + " " + monthNames[monthIndex];
  
  return formattedDate;
}

function checkAndDisplayValidationErrors(input) {
    $('.errorMessages').empty();

    var errorMessages = [];


    input.each(function () {

        var element = $(this);
        var errorField = $('label[for=' + this.id + ']').text();

      //  && element[0].val().length != 0 && ((!isNaN(element[0].val()) && element[0].val().length != 5) || isNaN(searchTermInput.val())))) 
        if (element.val().length !=5 && !isNaN(element.val()) ||  isNaN(element.val())){            
            errorMessages.push(errorField + ' ' + 'Please enter a 5-digit zip code');
        }
    });


    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('.errorMessages').append($('<li>').attr({ class: 'list-group-item list-group-item-danger' }).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}
