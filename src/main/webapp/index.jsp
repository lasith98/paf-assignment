<%@ page import="lk.sliit.pafassignment.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Customer</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">

</head>
<body>


<div class="container">

    <h5 class="mt-2 mb-4">Customer Management</h5>

    <div class="mb-4">

        <form method="post" action="${pageContext.request.contextPath}/CustomerAPI" id="customer-form">

            <input type="hidden" name="customerId" id="customerId" value="">

            <div class="row">
                <div class="col">
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" aria-describedby="firstName"
                               name="firstName">
                    </div>
                </div>
                <div class="col">
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" aria-describedby="lastName"
                               name="lastName">
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col">
                    <div class="mb-3">
                        <label for="mobileNo" class="form-label">Mobile No</label>
                        <input type="text" class="form-control" id="mobileNo" aria-describedby="mobileNo"
                               name="mobileNo" maxlength="10">
                    </div>


                </div>
                <div class="col">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="text" class="form-control" id="email" aria-describedby="email" name="email">
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label for="nic" class="form-label">NIC</label>
                <input type="text" class="form-control" id="nic" aria-describedby="nic" name="nic">
            </div>


            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <input type="text" class="form-control" id="address" aria-describedby="address" name="address">
            </div>


            <button type="button" class="btn btn-primary" id="save-btn" style="width: 100px;margin-right: 20px">Save</button>

            <button type="button" class="btn btn-danger" id="cancel-btn" style="width: 100px">Cancel</button>

        </form>


    </div>

    <div id="alertSuccess" class="alert alert-success"></div>
    <div id="alertError" class="alert alert-danger"></div>


    <div id="customerTable">
        <%
            Customer customer = new Customer();
            out.print(customer.readCustomers());
        %>
    </div>


    <table>
        <thead></thead>
    </table>


</div>


<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/components/customer.js"></script>
</body>
</html>