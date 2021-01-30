var Sys = {
	/**
	 * 初始化表格
	 * 
	 * @param curParam
	 */
	initTable : function(curParam) {

		var tableId = curParam.tableId;
		var url = curParam.url;
		var queryParams = curParam.queryParams;
		var columns = curParam.columns;

		var onEditableSave = curParam.onEditableSave;
		if (!onEditableSave) {
			onEditableSave = function() {
			};
		}

		$('#' + tableId).bootstrapTable({
			method : 'get',
			// toolbar: '#toolbar', //工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 20, // 每页的记录行数（*）
			// pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
			url : url,// 这个接口需要处理bootstrap table传递的固定参数
			queryParamsType : '', // 默认值为 'limit' ,在默认情况下
			// 传给服务端的参数为：offset,limit,sort
			// 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber

			queryParams : function(tabParams) {
				var pageParam = {
					pageNumber : tabParams.pageNumber,

					pageSize : tabParams.pageSize
				};

				var param = $.extend({}, queryParams, pageParam);
				// alert(JSON.stringify(param));

				return param;

			},

			// queryParams :queryParams

			// 前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			// search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
			// showColumns: true, //是否显示所有的列
			// showRefresh: true, //是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			searchOnEnterKey : true,
			columns : columns,
			pagination : true,

			onClickCell : function(field, value, row, $element) {
				// alert(field);
				// alert(value);
				// alert();
				return false;

			},

			onEditableSave : onEditableSave
		});

	},

	refreshTable : function(curParam) {

		$("#" + curParam.tableId).bootstrapTable('refresh', {
			url : curParam.url,
			query : curParam.queryParams
		});

	},

	initSubmit : function(curParam) {

		var defaultParam = {
			formId : "newForm",
			rules : {},
			submitHandler : function() {
			}
		};
		var param = $.extend({}, defaultParam, curParam);

		var form1 = $('#' + param.formId);
		var error1 = $('.alert-danger', form1);
		var success1 = $('.alert-success', form1);

		form1
				.validate({
					errorElement : 'span', // default input error message
					// container
					errorClass : 'help-block help-block-error', // default input
					// error
					// message class
					focusInvalid : false, // do not focus the last invalid
					// input
					ignore : "", // validate all fields including form hidden
					// input
					messages : param.messages,
					rules : param.rules,

					invalidHandler : function(event, validator) { // display
						// error
						// alert on form
						// submit
						success1.hide();
						error1.show();
						App.scrollTo(error1, -200);
					},

					errorPlacement : function(error, element) {
						if (element.is(':checkbox')) {
							error
									.insertAfter(element
											.closest(".md-checkbox-list, .md-checkbox-inline, .checkbox-list, .checkbox-inline"));
						} else if (element.is(':radio')) {
							error
									.insertAfter(element
											.closest(".md-radio-list, .md-radio-inline, .radio-list,.radio-inline"));
						} else {
							error.insertAfter(element); // for other inputs,
							// just
							// perform default behavior
						}
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error'); // set
						// error
						// class to
						// the
						// control
						// group
					},

					unhighlight : function(element) { // revert the change
						// done by
						// hightlight
						$(element).closest('.form-group').removeClass(
								'has-error'); // set
						// error
						// class
						// to
						// the
						// control
						// group
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); // set
						// success
						// class
						// to
						// the
						// control
						// group
					},

					submitHandler : function(form) {
						// success1.show();
						// error1.hide();

						param.submitHandler(param.submitParam);
					}
				});

	},

	submitForm : function(curParam) {

		if (!curParam.ajaxUrl) {
			alert("请设置ajaxUrl！");
			return;
		}
		if (!curParam.successUrl && !curParam.successCallback) {
			alert("请设置successUrl 或 successCallback！");
			return;
		}

		var defaultParam = {
			dialogType : "alert",
			btnSubmitingVal : "保存中...",
			btnVal : "保存",
			btnId : "btnSave",
			ajaxType : "post",
			formId : "newForm",
			
			// ajaxUrl:"",
			// successUrl:"",
			successTip : "操作成功!"

		};

		var param = $.extend({}, defaultParam, curParam);
		// alert(JSON.stringify(param));
		
		var postData=JSON.stringify($("#" + param.formId).obj());
		if(param.buildData){
			
			postData=param.buildData();
		}

		$("#" + param.btnId).val(param.btnSubmitingVal).attr("disabled", true);
		// 调用初始化表单方法
		if (param.initForm) {

			if (!param.initForm()) {

				return;
			}

		}
		;

		$.ajax({
			url : param.ajaxUrl,
			type : param.ajaxType,
			dataType : "json",
			contentType : "application/json",
			data : postData,
			success : function(resp) {

				if (param.successCallback) {
					param.successCallback(resp);
					return;
				}

				$("#" + param.btnId).val(param.btnVal).removeAttr("disabled");
				if (!resp.success) {
					// 提示信息
					Sys.alert(resp.resultMsg);
					return;
				}

				Sys.alertSuccess({
					"successMsg" : param.successTip,
					"callbackFunc" : function() {
						location.href = param.successUrl;
					}
				});

			},
			error : function(resp) {
				// location.href =
				// "/admin/err/500.html";
				alert(resp.resultCode);
			}
		});

	},

	timeFormatter : function(value, row, index) {

		var d = new Date();
		d.setTime(value);
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();

		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		//
		month = month < 10 ? "0" + month : month;
		date = date < 10 ? "0" + date : date;
		hour = hour < 10 ? "0" + hour : hour;
		minute = minute < 10 ? "0" + minute : minute;
		second = second < 10 ? "0" + second : second;

		var html = year + "-" + month + "-" + date + " " + hour + ":" + minute
				+ ":" + second;
		return html;
	},

	unixTimeFormatter : function(value, row, index) {

		var d = new Date();
		d.setTime(value * 1000);
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();

		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		//
		month = month < 10 ? "0" + month : month;
		date = date < 10 ? "0" + date : date;
		hour = hour < 10 ? "0" + hour : hour;
		minute = minute < 10 ? "0" + minute : minute;
		second = second < 10 ? "0" + second : second;

		var html = year + "-" + month + "-" + date + " " + hour + ":" + minute
				+ ":" + second;
		return html;
	},

	imgFormatter : function(value, row, index) {

		// var html='<div class="" style="max-height: 60px; line-height:
		// 0px;">';
		html = '<img src="' + value + '" style="max-height: 50px;">';
		// html+='</div>';

		return html;
	},

	initUpload : function(curParam) {

		var defaultParam = {
			uploadUrl : "/upload?confCode=",
			maxFileSize : 1500,
			allowedFileExtensions : [ "jpg", "png", "gif" ],
			defaultPreviewContent : '<img src="/images/default_avatar_male.jpg" style="max-height:160px"><h6 class="text-muted">点击选择</h6>',
			fileuploaded : function(event, data, previewId, index) {

			}

		};

		var param = $.extend({}, defaultParam, curParam);

		//alert(param.defaultPreviewContent);
		$("#" + param.fileId)
				.fileinput(
						{

							uploadUrl : param.uploadUrl,
							 language: 'zh',

							uploadAsync : true,
							overwriteInitial : false,
							maxFileSize : 1500,
							msgProgress : "",
							msgUploadEnd : "OK",
							showClose : false,
							showCaption : false,
							showBrowse : false,
							showRemove : false, // 显示移除按钮
							browseOnZoneClick : true,
							removeLabel : '',

							removeTitle : 'Cancel or reset changes',
							elErrorContainer : '#kv-avatar-errors-2',
							msgErrorClass : 'alert alert-block alert-danger',
							defaultPreviewContent : param.defaultPreviewContent,
							layoutTemplates : {
								main2 : '{preview}{remove} {browse}'
							},

							fileActionSettings : {
								
								removeIcon : '<i class="fa fa-trash text-danger"></i>',
								
								uploadIcon : '<i class="fa fa-upload text-info"></i>',
								
								uploadRetryIcon : '<i class="fa fa-repeat text-info"></i>',
								
								zoomIcon : '<i class="fa fa-search-plus"></i>',
								zoomClass : 'btn btn-xs btn-default',
								
								dragIcon : '<i class="glyphicon glyphicon-menu-hamburger"></i>',
							
								indicatorNew : '<i class="fa fa-hand-o-down text-warning"></i>',
								indicatorSuccess : '<i class="fa fa-check-circle text-success"></i>',
								indicatorError : '<i class="glyphicon glyphicon-exclamation-sign text-danger"></i>',
								indicatorLoading : '<i class="fa fa-hand-o-up text-muted"></i>',
								

							},
							
							
							previewZoomButtonIcons: {
					            prev: '<i class="fa fa-arrow-left"></i>',
					            next: '<i class="fa fa-arrow-right"></i>',
					            toggleheader: '<i class="fa fa-arrows-v"></i>',
					            fullscreen: '<i class="fa fa-arrows-alt"></i>',
					            borderless: '<i class="fa fa-expand"></i>',
					            close: '<i class="fa fa-times"></i>'
					        },
							

							allowedFileExtensions : param.allowedFileExtensions,

							actionDelete : '<button type="button" class="kv-file-remove {removeClass}" title="{removeTitle}"{dataUrl}{dataKey}>{removeIcon}ssss</button>\n'

						});

		$("#" + param.fileId).on('fileselect',
				function(event, numFiles, label) {
					// alert("fileselect");
					// 方法链
					$("#" + param.fileId).fileinput('upload');
				});

		$("#" + param.fileId).on('filedeleted', function(event, key) {
			console.log('Key = ' + key);
		});

		$("#" + param.fileId).on('fileuploaded',
				function(event, data, previewId, index) {

					// alert(fileUrl);
					$("#" + param.fileId).fileinput('enable');

					param.fileuploaded(event, data, previewId, index);

				});

	},

	initUploadFile : function(curParam) {
		var defaultParam = {

		};

		var param = $.extend({}, defaultParam, curParam);

		$("#" + param.fileId).fileinput({

			removeLabel : "删除",
			uploadLabel : "上传",
			browseLabel : "浏览",
			dropZoneTitle : "拖拽文件到这里 &hellip;<br>支持多文件同时上传",
			uploadUrl : param.uploadUrl,
			uploadAsync : false,
			previewFileIcon : '<i class="fa fa-file"></i>',
			preferIconicZoomPreview : true, // this will force zoom preview
			// thumbnails to display icons for
			// following file extensions
			fileActionSettings : {
				
				removeIcon : '<i class="fa fa-trash text-danger"></i>',
				
				uploadIcon : '<i class="fa fa-upload text-info"></i>',
				
				uploadRetryIcon : '<i class="fa fa-repeat text-info"></i>',
				
				zoomIcon : '<i class="fa fa-search-plus"></i>',
				zoomClass : 'btn btn-xs btn-default',
				
				dragIcon : '<i class="glyphicon glyphicon-menu-hamburger"></i>',
			
				indicatorNew : '<i class="fa fa-hand-o-down text-warning"></i>',
				indicatorSuccess : '<i class="fa fa-check-circle text-success"></i>',
				indicatorError : '<i class="glyphicon glyphicon-exclamation-sign text-danger"></i>',
				indicatorLoading : '<i class="fa fa-hand-o-up text-muted"></i>',
			},
			
			
			previewZoomButtonIcons: {
	            prev: '<i class="fa fa-arrow-left"></i>',
	            next: '<i class="fa fa-arrow-right"></i>',
	            toggleheader: '<i class="fa fa-arrows-v"></i>',
	            fullscreen: '<i class="fa fa-arrows-alt"></i>',
	            borderless: '<i class="fa fa-expand"></i>',
	            close: '<i class="fa fa-times"></i>'
	        },
			
			
			previewFileIconSettings : { // configure your icon file extensions
				'doc' : '<i class="fa fa-file-word-o text-primary"></i>',
				'xls' : '<i class="fa fa-file-excel-o text-success"></i>',
				'ppt' : '<i class="fa fa-file-powerpoint-o text-danger"></i>',
				'pdf' : '<i class="fa fa-file-pdf-o text-danger"></i>',
				'zip' : '<i class="fa fa-file-archive-o text-muted"></i>',
				'rar' : '<i class="fa fa-file-archive-o text-muted"></i>',
				'htm' : '<i class="fa fa-file-code-o text-info"></i>',
				'txt' : '<i class="fa fa-file-text-o text-info"></i>',
				'mov' : '<i class="fa fa-file-movie-o text-warning"></i>',
				'mp3' : '<i class="fa fa-file-audio-o text-warning"></i>',
				// note for these file types below no extension determination
				// logic
				// has been configured (the keys itself will be used as
				// extensions)
				'jpg' : '<i class="fa fa-file-photo-o text-danger"></i>',
				'gif' : '<i class="fa fa-file-photo-o text-warning"></i>',
				'png' : '<i class="fa fa-file-photo-o text-primary"></i>'
			},
			previewFileExtSettings : { // configure the logic for determining
				// icon file extensions
				'doc' : function(ext) {
					return ext.match(/(doc|docx)$/i);
				},
				'xls' : function(ext) {
					return ext.match(/(xls|xlsx)$/i);
				},
				'ppt' : function(ext) {
					return ext.match(/(ppt|pptx)$/i);
				},

				'htm' : function(ext) {
					return ext.match(/(htm|html)$/i);
				},
				'txt' : function(ext) {
					return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
				},
				'mov' : function(ext) {
					return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
				},
				'mp3' : function(ext) {
					return ext.match(/(mp3|wav)$/i);
				}
			}
		});

		$("#" + param.fileId).on('fileuploaded',
				function(event, data, previewId, index) {

					// alert(fileUrl);
					$("#" + param.fileId).fileinput('enable');
					// var fileUrl = data.response.data[0].fileUrl;
					// alert(fileUrl);
					// $("#downloadUrl").val(fileUrl);
					param.fileuploaded(event, data, previewId, index);

				});

	},

	alert : function(msg) {

		swal(msg);

	},

	alertSuccess : function(curParam) {
		var defaultParam = {
			"successMsg" : "操作成功！",
			"callbackFunc" : function() {
				return true;
			}
		};

		var param = $.extend({}, defaultParam, curParam);

		swal({
			title : "提示",
			text : param.successMsg,
			type : "success",
			showCancelButton : false,
			// confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			// cancelButtonText : "取消",
			closeOnConfirm : true
		}, function() {

			param.callbackFunc();

		});

	},

	confirm : function(curParam) {
		var defaultParam = {
			"successMsg" : "操作成功！",
			"callbackFunc" : function() {
				return true;
			}
		};

		var param = $.extend({}, defaultParam, curParam);

		// e.preventDefault();

		swal({
			title : "提示",
			text : param.showMsg,
			type : "warning",
			showCancelButton : true,
			// confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
			closeOnConfirm : false
		}, function(isConfirm) {

			// 确定
			if (isConfirm) {

				// 回调函数执行完成
				if (param.callbackFunc(isConfirm)) {

					swal({
						title : "提示",
						text : param.successMsg,
						type : "success",
						showCancelButton : false,
						// confirmButtonColor : "#DD6B55",
						confirmButtonText : "确定",
						// cancelButtonText : "取消",
						closeOnConfirm : false
					});

				}
				
				
				

			}
			

		});

	}

}
