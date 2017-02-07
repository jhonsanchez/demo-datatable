<!DOCTYPE html>
<html lang="en">
<head>
    <link rel='stylesheet' href='/webjars/bootstrap/css/bootstrap.min.css'>
    <link rel='stylesheet' href='https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css'>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js/"></script>
    <script>
        $(document).ready(function () {
            $("#tabla").DataTable({
                "ajax": {
                    "url":"/data",
                    "dataSrc":""
                },
                "columns": [
                    {"data": "id"},
                    {"data": "name"}
                ]
            });
            $("#tabla-server").DataTable({
                processing:true,
                serverSide:true,
                "ajax": {
                    "url":"/data-server"
                },
                "lengthMenu": [[3, 25, 50, -1], [3, 25, 50, "All"]],
                start:0,
                length:3,
                "columns": [
                    {"data": "id"},
                    {"data": "name"}
                ]
            });
        });
    </script>
</head>
<body>
<h1>Hola Mundo</h1>
<h2>Tabla dinamica</h2>
<table id="tabla">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>
<h2>Tabla server side</h2>
<table id="tabla-server">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>
</body>
</html>
