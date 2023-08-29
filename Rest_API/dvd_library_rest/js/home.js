var selectedDvdId = null;

$(document).ready(function () {
    loadDvds("");
    showCreateform();
    addDvd();
    updateDvd();
    searchDvd();
});

function loadDvds(searchInput) {
    clearDvdTable();
    var contentRows = $('#contentRows');

    $.ajax({
        type: 'GET',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvds/' + searchInput,
        success: function (dvdArray) {
            $.each(dvdArray, function (index, dvd) {
                var title = dvd.title;
                var releaseYear = dvd.releaseYear;
                var director = dvd.director;
                var rating = dvd.rating;
                var dvdId = dvd.id;


                var row = '<tr>';
                row += '<td><button type="button" class="btn btn-link titleButton" onclick="displayDvdDetails(' + dvdId + ')">' + title + '</button> </td>';
                row += '<td>' + releaseYear + '</td>';
                row += '<td>' + director + '</td>';
                row += '<td>' + rating + '</td>';
                row += '<td><button type="button" class="btn btn-link editButton" onclick="showEditForm(' + dvdId + ')">Edit</button>' +
                    '|' +
                    '<button type="button" class="btn btn-link deleteButton" data-toggle="modal" data-target="#exampleModal" onclick="selectedDvdId = ' + dvdId + '" >Delete</button></td>';
                row += '</tr>';

                contentRows.append(row);
            })

        },
        error: function () {
            $('.errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    })
}

function showCreateform() {


    $('#createButton').click(function (event) {
        $('.errorMessages').empty();
        $('#loadingPageDiv').hide();
        $('#createFormDiv').show();

    })
}



function addDvd() {
    $('#addButton').click(function (event) {
        $('.errorMessages').empty();
        var haveValidationErrors = checkAndDisplayValidationErrors($('#createForm').find('input'));

        if (haveValidationErrors) {
            return false;
        }
        $.ajax({
            type: 'POST',
            url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd',
            data: JSON.stringify({
                title: $('#addTitle').val(),
                releaseYear: $('#addReleaseYear').val(),
                director: $('#addDirector').val(),
                rating: $('#addRating').val(),
                notes: $('#addNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function () {
                alert("The Dvd was successfully added!")
                $('.errorMessages').empty();
                hideCreateForm();
                loadDvds("");

            },
            error: function () {
                $('.errorMessages')
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger' })
                        .text('Error calling web service. Please try again later.'));
            }
        })
    });
}



function showEditForm(dvdId) {

    $('.errorMessages').empty();

    $.ajax({
        type: 'GET',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
        success: function (data, status) {
            $('#editDvdPageTitle').text('Edit Dvd: ' + data.title);
            $('#editTitle').val(data.title);
            $('#editReleaseYear').val(data.releaseYear);
            $('#editDirector').val(data.director);
            $('#editRating').val(data.rating);
            $('#editNotes').val(data.notes);
            $('#editDvdId').val(data.id);


        },
        error: function () {
            $('.errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    })
    $('#loadingPageDiv').hide();
    $('#editFormDiv').show();


}

function updateDvd() {

    $('#updateButton').click(function (event) {
        $('.errorMessages').empty();

        var haveValidationErrors = checkAndDisplayValidationErrors($('#editForm').find('input'));

        if (haveValidationErrors) {
            return false;
        }

        $.ajax({
            type: 'PUT',
            url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + $('#editDvdId').val(),
            data: JSON.stringify({
                title: $('#editTitle').val(),
                releaseYear: $('#editReleaseYear').val(),
                director: $('#editDirector').val(),
                rating: $('#editRating').val(),
                notes: $('#editNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            'success': function (data, textStatus, jqXHR) {
                if (jqXHR.status === 200) {
                    // Successful update
                    $('#errorMessage').empty();
                    alert("The Dvd was successfully updated!");
                    hideEditForm();
                    loadDvds("");
                } else {
                    // Handle unexpected status code here
                }
            },
            'error': function (jqXHR) {
                if (jqXHR.status === 200) {
                    // Successful update
                    $('.errorMessage').empty();
                    alert("The Dvd was successfully updated!");
                    hideEditForm();
                    loadDvds("");
                } else {
                    // Handle error
                    $('.errorMessages')
                        .append($('<li>')
                            .attr({ class: 'list-group-item list-group-item-danger' })
                            .text('Error calling web service. Please try again later.'));
                }
            }
        })


    })
}

function searchDvd() {
    $('#searchButton').click(function (event) {
        $('.errorMessages').empty();
        var inputCollection = $('#searchCategory, #searchTerm');

        var haveValidationErrors = checkAndDisplayValidationErrors(inputCollection);

        if (haveValidationErrors) {
            return false;
        }
        var searchInput = $('#searchCategory').val() + '/' + $('#searchTerm').val();
        loadDvds(searchInput);

    })
}

function displayDvdDetails(dvdId) {

    $('.errorMessages').empty();

    $.ajax({
        type: 'GET',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
        success: function (data, status) {
            $('#displayTitle').text(data.title);
            $('#displayReleaseYear').text(data.releaseYear);
            $('#displayDirector').text(data.director);
            $('#displayRating').text(data.rating);
            $('#displayNotes').text(data.notes);


        },
        error: function () {
            $('.errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    })
    $('#loadingPageDiv').hide();
    $('#displayFormDiv').show();


}

function deleteDvd(dvdId) {

    $.ajax({
        type: 'DELETE',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
        success: function () {
            loadDvds("");
        }
    });

}

function clearDvdTable() {
    $('#contentRows').empty();
}

function hideCreateForm() {
    $('.errorMessages').empty();

    $('#addTitle').val('');
    $('#addReleaseYear').val('');
    $('#addDirector').val('');
    $('#addRating').val('G');
    $('#addNotes').val('');


    $('#loadingPageDiv').show();
    $('#createFormDiv').hide();
}

function hideEditForm() {
    $('.errorMessages').empty();

    $('#editTitle').val('');
    $('#editReleaseYear').val('');
    $('#editDirector').val('');
    $('#editRating').val('');
    $('#editNotes').val('');

    $('#loadingPageDiv').show();
    $('#editFormDiv').hide();
}

function hidedisplayForm() {
    $('.errorMessages').empty();

    $('#displayTitle').val('');
    $('#displayReleaseYear').val('');
    $('#displayDirector').val('');
    $('#displayRating').val('');
    $('#displayNotes').val('');

    $('#loadingPageDiv').show();
    $('#displayFormDiv').hide();
}

function checkAndDisplayValidationErrors(input) {
    $('.errorMessages').empty();

    var errorMessages = [];

    var customErrorMessages = {
        'searchCategory': 'Please select a search category',
        'searchTerm': 'Search term is required',
        'editTitle': 'Please enter a title for the Dvd',
        'addTitle': 'Please enter a title for the Dvd'

    };
    var conditionStart = false;

    input.each(function () {

        var element = $(this);

        var searchTermInput;
        if (element.val() === 'year') {
            searchTermInput = $('#searchTerm');
        } else if (element.attr('id') === 'editReleaseYear') {
            searchTermInput = $('#editReleaseYear');
        } else {
            searchTermInput = $('#addReleaseYear');
        }

        var errorField = $('label[for=' + this.id + ']').text();


        if (!element[0].validity.valid || $(element[0]).val() === null || ((element.val() === 'year' || element.attr('id') === 'editReleaseYear' || element.attr('id') === 'addReleaseYear') && searchTermInput.val().length != 0 && ((!isNaN(searchTermInput.val()) && searchTermInput.val().length != 4) || isNaN(searchTermInput.val())))) {
            var customErrorMessage = customErrorMessages[element.attr('id')];
            if (element.val() === 'year' || (element.attr('id') === 'editReleaseYear' || element.attr('id') === 'addReleaseYear') && searchTermInput.val().length != 0) {
                customErrorMessage = 'Please enter a 4-digit year';
            }
            if ((element.attr('id') === 'searchCategory' || element.attr('id') === 'searchTerm') && $('#searchCategory').val() == null && $('#searchTerm').val() === '') {

                customErrorMessage = 'Both Search Term and Search Category are required.';
                conditionStart = true;
            }
            errorMessages.push(customErrorMessage || (errorField + ' ' + this.validationMessage));
        }
    });

    if (conditionStart) {
        errorMessages.pop();
    }

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