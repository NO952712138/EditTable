	$(function(){
		
		/*分页开始*/
		var pageNow=1;
		var totalPage=0;
		var pageSize=10;
		getDataByPage(pageNow,pageSize);
		$("#previous").click(function(){
			if(pageNow<=1)
				{
				alert("当前已是第一页");
				}
			else
				{
				pageNow--;
				getDataByPage(pageNow,pageSize);
				}
		});
		$("#next").click(function(){
			if(pageNow==totalPage)
				{
				alert("我是有底线的");
				}else
					{
					pageNow++;
					getDataByPage(pageNow,pageSize);
					}
		})
		$("#goto").click(function()
				{
			if($("#pageNo").val()>=totalPage)
				{
				alert("输入不合法！");
				}
			else
				{
				
				getDataByPage($("#pageNo").val(),pageSize);
				}
				})
				
		var td=$("tbody td");
		td.click(function(){
			var inputObj=$("<input type='text'/>");
			var tdObj=$(this);
			if(tdObj.children("input").length>0)
			{
				return false;
			}
			if(tdObj.children("span").length>0)
			{
				return;
			}
			var text=tdObj.html();
			console.log(tdObj.width());
			inputObj.css("border-width","0px")
			.width(tdObj.width())
			.css("font-size","16px")
			.css("background-color",tdObj.css("background-color"))
			.val(text);

			tdObj.html("");
			inputObj.appendTo(tdObj);
			inputObj.trigger("focus").trigger("select");
			inputObj.click(function(){
				return false;
			});
			inputObj.keyup(function(){
				var keycode=event.which;
				console.log(keycode);
				if(keycode==13)
				{
					var content=$(this).val();
					tdObj.html(content);
				}
				if(keycode==27)
				{
					tdObj.html(text);
				}
			});

		});
		$("#dataTable").on("click",".glyphicon-remove",function(){
			console.log("ddd");
			var tr=$(this).parent().parent();
			$(tr).remove();
		});
		$("#dataTable").on("click",".glyphicon-trash",function(){
			
			var th=$(this).parent();
			var index=$("#dataTable").find("th").index($(th));			
			$(th).remove();
			var trs=$("#dataTable").find("tr");
			$(trs).each(function()
			{
				var td=$(this).children("td").eq(index);
				$(td).remove();
			})

		});
	});
	
	function getDataByPage(pageNow,pageSize)
	{
		$.ajax({
			url:"http://localhost:8080/querydata",
			data:{
				pageSize:pageSize,
				pageNow:pageNow
			},
			type:'POST',
			dataType:'json',
			async:false,
			success:function(data)
			{
				if(data.length!=0)
					{
					$("#dataTable").find("tbody").children("tr").empty();
					$("#totalPage").text("共 "+Math.ceil(data.total/pageSize)+" 页");
					$("#nowPageNo").text("当前为第 "+pageNow+"页");
					totalPage=data.total;
					var htmlstr="";
					for(var i=0;i<data.data.length;i++)
						{
						htmlstr+="<tr><td>"+data.data[i].a1+"</td>"
						+"<td>"+data.data[i].a2+"</td>"
						+"<td>"+data.data[i].a3+"</td>"
						+"<td>"+data.data[i].a4+"</td>"
						+"<td>"+data.data[i].a5+"</td>"
						+"<td>"+data.data[i].a6+"</td>"
						+"<td>"+data.data[i].a7+"</td>"
						+"<td>"+data.data[i].a8+"</td>"
						+"<td>"+data.data[i].a9+"</td>"
						+"<td>"+data.data[i].a10+"</td>"
						+"<td>"+data.data[i].a11+"</td>"
						+"<td>"+data.data[i].a12+"</td>"
						+"<td>"+data.data[i].quality+"</td>"
						+"<td><span class=\"glyphicon glyphicon-remove\" arial-hidden=\"true\"></span></td>"
						+"</tr>";
						}
					$("#dataTable").find("tbody").append(htmlstr);
					}
			}
		});
	}
	