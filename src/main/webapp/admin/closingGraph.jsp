<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ==================================================================== -->
	<title>Ezen Fulfillment System</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../css/bootstrap.vertical-tabs.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../common/_top_purchase.jspf" %>
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div class="col-md-2">
				<%@ include file="../common/_admin_left_closing.jspf" %>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-7"><h3>정산 내역 (구매 가상 데이터)</h3></div>
					<div class="col-md-5">	</div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-12">
						<p style="text-align: right;">(단위: 천원)&nbsp;&nbsp;&nbsp;</p>
						<canvas id="myChart" height="140"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/_bottom.jspf" %>
	<!-- ==================================================================== -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script>
		var ctx = document.getElementById("myChart").getContext('2d');
		/*
		- Chart를 생성하면서, 
		- ctx를 첫번째 argument로 넘겨주고, 
		- 두번째 argument로 그림을 그릴때 필요한 요소들을 모두 넘겨줍니다. 
		*/
		var records = new Array();
		<c:set var="records" value="${requestScope.ClosingRecords}"/>
        <c:forEach var="record" items="${records}">
			records.push("${record}");
		</c:forEach>
		var records2 = new Array();
		<c:set var="records2" value="${requestScope.ClosingRecords2}"/>
		<c:forEach var="record2" items="${records2}">
			records2.push("${record2}");
		</c:forEach>
		var myChart = new Chart(ctx, {
		    type: 'line',
		    data: {
		        labels: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		        datasets: [{
		            label: '삼송전자',
		            data: records,
		            borderColor: ['rgba(255, 99, 132, 1)'],
		            backgroundColor: ['rgba(255, 99, 132, 1)'],
		            fill: false,
		            borderWidth: 3
		        }, {
		            label: 'LS전자',
		            data: records2,
		            borderColor: ['rgba(54, 112, 235, 1)'],
		            backgroundColor: ['rgba(54, 112, 235, 1)'],
		            fill: false,
		            borderWidth: 3
		        }, {
		        	label: '트윈스스포츠',
		            data: ['6000', '4500', '4000', '5500', '6000', '8900', '5000', '4800', '4000', '5500', '5200', '4900'],
		            borderColor: ['rgba(225, 186, 106, 1)'],
		            backgroundColor: ['rgba(225, 186, 106, 1)'],
		            fill: false,
		            borderWidth: 3
		        }, {
		        	label: '신선식품',
		            data: ['15000', '14500', '14000', '16500', '16000', '13900', '15000', '17800', '14000', '15500', '16200', '15900'],
		            borderColor: ['rgba(85, 232, 122, 1)'],
		            backgroundColor: ['rgba(85, 232, 122, 1)'],
		            fill: false,
		            borderWidth: 3
		        }, {
		        	label: '중부식품',
		        	data: ['25000', '24500', '34000', '25500', '26000', '28900', '25000', '34800', '34000', '25500', '25200', '34900'],
		            borderColor: ['rgba(153, 102, 255, 1)'],
		            backgroundColor: ['rgba(153, 102, 255, 1)'],
		            fill: false,
		            borderWidth: 3
		        }]
		    },
		    options: {
		        maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
		        scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero: true
		                }
		            }]
		        },
			    elements: {
		            line: {
		                tension: 0 // disables bezier curves
		            }
		        }
		    }
		});
	</script>
</body>
</html>