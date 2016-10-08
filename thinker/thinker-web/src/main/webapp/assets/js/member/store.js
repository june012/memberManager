//弹出窗口
var schoolList;
	function pop(){
		//将窗口居中
		makeCenter();
		$.ajax({
			type: 'POST',
			url: '/member/getStoreAndMember',
			dataType:'json',
			success: function(data){
				schoolList=data;
				//初始化省份列表
				initProvince();
				//默认情况下, 给第一个省份添加choosen样式
				$('[province-id="0"]').addClass('choosen');
				//初始化大学列表
				initSchool(1);
			}
		});



	}
	//隐藏窗口
	function hide()
	{
		$('#choose-box-wrapper').css("display","none");
	}

	function initProvince()
	{
		//原先的省份列表清空
		$('#choose-a-province').html('');
		var appendStr = '<select id="selectStore">';
		for(var i=0;i<schoolList.length;i++)
		{
			appendStr+='<option class="province-item" value="'+schoolList[i].storeId+'" province-id="'+schoolList[i].id+'">'+schoolList[i].name+'</option>';
		}
		appendStr+='</select>'
		appendStr+='&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="school" id="phone" placeholder="手机号查询" /><button id="find"">查询</button>';
		$('#choose-a-province').append(appendStr);

		//查询事件
		$('#find').bind('click', function(){
				var phone = $('#phone').val();
				$.ajax({
					type: 'POST',
					url: '/member/queryMemberByPhone',
					dataType:'json',
					data: { "phone": phone},
					success: function(data){
						$('#choose-a-school').html('');
						if(typeof(data) == "undefined"){
							return;
						}
						var memberDtos = data.memberDtos;
						$("#selectStore option[value='"+data.storeId+"']").attr("id")
						$('#choose-a-school').append('<a class="school-item"  school-id="'+memberDtos[0].id+'">'+memberDtos[0].name+'</a>');

						//添加大学列表项的click事件
						$('.school-item').bind('click', function(){
								var item=$(this);
								var school = item.attr('school-id');

								//更新选择大学文本框中的值
								$('#member-name').val(item.text());
								//关闭弹窗
								hide();
							}
						);
					}
				});

			}
		);
		//添加省份列表项的change事件
		$('#choose-a-province').bind('change', function(){
				var item=$($('.province-item:selected'));
				var province = item.attr('province-id');
				var choosenItem = item.parent().find('.choosen');
				if(choosenItem)
					$(choosenItem).removeClass('choosen');
				item.addClass('choosen');
				//更新大学列表
				initSchool(province);
			}
		);
	}

	function initSchool(provinceID)
	{
		//原先的学校列表清空
		$('#choose-a-school').html('');
		var memberDtos =[];
		if(typeof(schoolList[provinceID-1]) != "undefined"){
			memberDtos = schoolList[provinceID-1].memberDtos;
		}else{
			return;
		}

		for(i=0;i<memberDtos.length;i++)
		{
			$('#choose-a-school').append('<a class="school-item" school-id="'+memberDtos[i].id+'">'+memberDtos[i].name+'</a>');
		}
		//添加大学列表项的click事件
		$('.school-item').bind('click', function(){
				var item=$(this);
				var school = item.attr('school-id');
				//更新选择大学文本框中的值
				$('#member-name').val(school);
				//关闭弹窗
				hide();
			}
		);
	}

	function makeCenter()
	{
		$('#choose-box-wrapper').css("display","block");
		$('#choose-box-wrapper').css("position","absolute");
		$('#choose-box-wrapper').css("top", Math.max(0, (($(window).height() - $('#choose-box-wrapper').outerHeight()) / 2) + $(window).scrollTop()) + "px");
		$('#choose-box-wrapper').css("left", Math.max(0, (($(window).width() - $('#choose-box-wrapper').outerWidth()) / 2) + $(window).scrollLeft()) + "px");
	}