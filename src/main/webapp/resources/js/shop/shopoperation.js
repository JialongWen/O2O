/**
 * 
 */
$(function() {
	var shopId = getQueryString('shopId');
	var isEdit = shopId?true:false;
	var initUrl = '/myo2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/myo2o/shopadmin/registershop';
	var shopInfoUrl = "/myo2o/shopadmin/getshopbyid?shopId=" + shopId;
	var editShopUrl = '/myo2o/shopadmin/modifyshop';
	
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId)
	}

	//更新
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				shop.shopName = $('#shop-name').val(shop.shopName);
				shop.shopAddr = $('#shop-addr').val(shop.shopAddr);
				shop.phone = $('#shop-phone').val(shop.phone);
				shop.shopDesc = $('#shop-desc').val(shop.shopDesc);
				
				var shopCategory = '<option data-id="' 
					+ shop.shopCategory.shopCategoryId+ '" selected>'
					+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+ shop.area.areaId +"']").attr("selected","selected");
			}
		});
	}
	
	//注册
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml ='';
				var tempAreaHtml ='';
				data.shopCategoryList.map(function(item, index){
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
		$('#submit').click(function() {
					var shop = {};
					if(isEdit){
						shop.shopId = shopId;
					}
					shop.shopName = $('#shop-name').val();
					shop.shopAddr = $('#shop-addr').val();
					shop.phone = $('#shop-phone').val();
					shop.shopDesc = $('#shop-desc').val();
					shop.shopCategory = {
						shopCategoryId : $('#shop-category').find('option').not(function() {
									return !this.selected;
								}).data('id')
					};
					shop.area = {
						areaId : $('#area').find('option').not(function() {
							return !this.selected;
						}).data('id')
					};
					var shopImg = $('#shop-img')[0].files[0];
					var formData = new FormData();
					formData.append('shopImg', shopImg);
					formData.append('shopStr', JSON.stringify(shop));
					var VerifyCodeActual = $('#j_captcha').val();
					if(!VerifyCodeActual){
						$.toast('请输入验证码！');
						return;
					}
					formData.append('VerifyCodeActual',VerifyCodeActual);
					$.ajax({
						url:(isEdit ? editShopUrl : registerShopUrl),
						type:'POST',
						data:formData,
						contentType:false,
						processData:false,
						cache:false,
						success:function(data) {
							if(data.success){
								$.toast('提交成功');
							}else{
								$.toast('错误'+data.errMsg);
							}
							$('#captcha_img').click();
						}
					});
				});
});