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
</head>
<body>
	<%@ include file="../common/_top.jspf" %>
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div class="col-md-2">
				<div class="list-group">
					<a href="../deliver/deliverServlet?action=list" class="list-group-item">출고대기 목록</a>
					<a href="../deliver/deliverServlet?action=releaseList" class="list-group-item">일별 출고내역</a>
					<a href="../deliver/deliverServlet?action=releaseMonthly&page=1" class="list-group-item">월별 출고내역</a>
					<a href="#" class="list-group-item active">정산 내역</a>
				</div>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-7"><h3>정산 내역 (가상 데이터 시뮬레이션)</h3></div>
					<div class="col-md-5">	</div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-12">
						<p style="text-align: right;">(단위: 원)&nbsp;</p>
						<div class="panel panel-primary">
							<table class="table table-striped table-condensed">
								<tr class="active">
									<c:forEach var="count" begin="1" end="12">
										<th class="col-md-1" style="text-align: right;">${count}월&nbsp;&nbsp;</th>
									</c:forEach>
								</tr>
								<tr>
									<c:set var="records" value="${requestScope.ClosingRecords}"/>
									<c:forEach var="record" items="${records}">
										<td style="text-align: right;">${record}</td>
									</c:forEach>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-md-12">
						<canvas id="myChart" height="120"></canvas>
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
        <c:forEach var="record" items="${records}">
			records.push("${record}");
		</c:forEach>
		var myChart = new Chart(ctx, {
		    type: 'bar',
		    data: {
		        labels: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		        datasets: [{
		            label: '정산 금액',
		            data: records,
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.3)',
		                'rgba(54, 162, 235, 0.3)',
		                'rgba(255, 206, 86, 0.3)',
		                'rgba(75, 192, 192, 0.3)',
		                'rgba(153, 102, 255, 0.3)',
		                'rgba(255, 159, 64, 0.3)',
		                'rgba(255, 99, 132, 0.3)',
		                'rgba(54, 162, 235, 0.3)',
		                'rgba(255, 206, 86, 0.3)',
		                'rgba(75, 192, 192, 0.3)',
		                'rgba(153, 102, 255, 0.3)',
		                'rgba(255, 159, 64, 0.3)'
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',
		                'rgba(54, 162, 235, 1)',
		                'rgba(255, 206, 86, 1)',
		                'rgba(75, 192, 192, 1)',
		                'rgba(153, 102, 255, 1)',
		                'rgba(255, 159, 64, 1)',
		                'rgba(255, 99, 132, 1)',
		                'rgba(54, 162, 235, 1)',
		                'rgba(255, 206, 86, 1)',
		                'rgba(75, 192, 192, 1)',
		                'rgba(153, 102, 255, 1)',
		                'rgba(255, 159, 64, 1)'
		            ],
		            borderWidth: 1
		        }]
		    },
		    options: {
		        maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
		        scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero:true
		                }
		            }]
		        }
		    }
		});
		</script>
</body>
</html>