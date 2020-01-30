$(function(){
	var shopId = getQueryString('shopId');
	var shopInfoUrl ='/myo2o/shopadmin/getshopmanagementinfo?shopId='+shopId;
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.href = data.Url;
		}else{
			if(data.shopId != undefined && data.shopId != null){
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href','/myo2o/shopadmin/shopoperation?shopId=' + shopId );
		}
	});
});
