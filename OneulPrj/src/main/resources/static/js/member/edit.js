window.addEventListener("load",function(){
	let checkid = document.querySelector(".checkid");
	
	$('.username').change(()=>{
		$('.checkid').show();
		$('#id_check_sucess').hide();
	})
	
	checkid.onclick=function(){
		$.ajax({
			url : "/member/checkid",
			type : "post",
			dataType : "json",
			data : {"username" : $('.username').val()},
			success : function(data){
				if(data==2){
					alert("아이디를 입력해주세요")
				}else if(data == 3){
					alert("아이디는 영문 소문자와 숫자 4~12자리로 입력해야합니다!");
				}else if(data == 1){
					alert("중복된 아이디입니다.");
				}else if(data == 0){
					alert("사용가능한 아이디입니다.");
					$('#id_check_sucess').show();
					$('.checkid').hide();
					return;
				}
			}
		})
	}

})


function check(){
	let form = document.querySelector(".edit-form");
	let username = document.getElementsByName("username")[0]
	let password = document.getElementsByName("password")[0]
	
	if(username.value == ''){
		alert("아이디를 입력하세요")
		return false;
	}
	let unexp = /^[a-z0-9]{4,12}$/
	if(!unexp.test(username.value)){
		alert("아이디는 영문 소문자와 숫자 4~12자리로 입력해야합니다!")
		return false;
	}
	
	if(password.value == ''){
		alert("비밀번호를 입력하세요")
		return false;
	}
	let pwdexp = /^[a-z0-9]{4,15}$/
	if(!pwdexp.test(password.value)){
		alert("비밀번호는 영문 소문자,숫자 4~15자리로 입력하세요")
		return false;
	}
	
	let checkedit = confirm("회원정보를 수정하시겠습니까?")
	if(checkedit == true){
		form.submit();
		alert("회원정보가 수정되었습니다. 다시 로그인해주세요")		
	}else{
		return window.location.href='edit'
	}
}


function outcheck(){
    let check = confirm("정말 탈퇴하시겠습니까?")
    if(check == true){
        alert("탈퇴 되었습니다.")
        return window.location.href='out'
    }else{
        return window.location.href='edit'
    }
}