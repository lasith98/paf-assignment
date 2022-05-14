$(document).ready(function () {
    clearAllAlert();

})

function clearAllAlert() {

    $("#alertSuccess").hide();
    $("#alertError").hide();

}

function showError(error) {
    console.log(error)
    const alertError = $("#alertError");
    $("#alertSuccess").hide();
    alertError.text(error)
    alertError.show();
}


function showSuccess(success) {
    const alertSuccess = $("#alertSuccess");
    $("#alertError").hide();
    alertSuccess.text(success)
    alertSuccess.show();
}


$(document).on("click", ".update-btn", function (event) {

    const tr = $(this).closest('tr');
    const data = tr.children('td').map(function () {
        return $(this).text().trim();
    }).get();
    $("#customerId").val($(this).attr("data-customerId").trim())
    $("#firstName").val(data[0])
    $("#lastName").val(data[1])
    $("#nic").val(data[2])
    $("#mobileNo").val(data[3])
    $("#email").val(data[4])
    $("#address").val(data[5])

})

$(document).on("click", ".remove-btn", function (event) {
    if (confirm("Are you sure you want to delete?")) {
        $.ajax(
            {
                url: "CustomerAPI",
                type: "DELETE",
                data: "customerId=" + $(this).attr("data-customerId"),
                dataType: "text",
                complete: function (response, status) {
                    onCustomerDeleteComplete(response.responseText, status);
                }
            });
    }

})



$(document).on("click", "#save-btn", function (event) {

    clearAllAlert();

    let status = validateCustomerForm();
    if (status !== true) {

        showError(status)
        return;
    }
    const customerForm = $("#customer-form");
    const customerId = $("#customerId");
    const type = (customerId.val() === "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "CustomerAPI",
            type: type,
            data: customerForm.serialize(),
            dataType: "text",
            complete: function (response, status) {
                onCustomerSaveAndUpdateComplete(response.responseText, status);
            }
        });

    customerForm[0].reset()
    customerId.val("")
})

$(document).on("click", "#cancel-btn", function (event) {
    $("#customer-form")[0].reset()
    $("#customerId").val("")
})

function validateCustomerForm() {

    if ($("#firstName").val().trim() === "") {
        return "Insert First Name!!";
    }

    if ($("#nic").val().trim() === "") {
        return "Insert Last Name!!";
    }

    if ($("#mobileNo").val().trim() === "") {
        return "Insert Mobile No!!";
    }

    if ($("#email").val().trim() === "") {
        return "Insert Email!!";
    }

    if ($("#address").val().trim() === "") {
        return "Insert Address!!";
    }

    return true;
}

function onCustomerSaveAndUpdateComplete(response, status) {
    if (status === "success") {

        const json = JSON.parse(response);
        if (json.status.trim() === "success") {

            showSuccess("Successfully saved.");
            $("#customerTable").html(json.data);

        } else if (json.status.trim() === "error") {
            showError(json.data)
        }

    } else if (status === "error") {
        showError("Error while saving.");
    } else {
        showError("Unknown error while saving..");
    }


}

function onCustomerDeleteComplete(response, status) {
    if (status === "success") {
        const json = JSON.parse(response);

        if (json.status.trim() === "success") {
            showSuccess("Successfully deleted.");
            $("#customerTable").html(json.data);

        } else if (json.status.trim() === "error") {
            showError(json.data)
        }

    } else if (status === "error") {

        showError("Error while deleting.");

    } else {

        showError("Unknown error while deleting..");

    }
}