/**
 * 
 */
  window.addEventListener("load",()=>{
	const fileInput = document.querySelector("input[type='file']");
	
	const fileDelBtn = document.querySelector(".file-del-btn");
	const changed = document.querySelector(".changed");
	
	const fileDelBtnContainer = document.querySelector(".del-btn-container");
	
	const img = document.querySelector(".image");
	
	img.onclick = function(e) {
		e.preventDefault();
		var event = new MouseEvent("click", { //이벤트 위임
			'view': window,
			'bubbles': true,
			'cancelable': true
		});
		fileInput.dispatchEvent(event);
	}

	
	
	
	fileInput.oninput = ()=>{
		let file = fileInput.files[0];
		if(file.type.indexOf("image/") < 0){
			alert("이미지 형식만 사용할 수 있습니다.");
			
			fileInput.value="";
			return;
		}
			
		let reader = new FileReader();
		
		reader.readAsDataURL(file);
		reader.onload = (e)=>{
			console.log("reader load");
			
			img.classList.remove("basic");
			img.src = e.target.result;
			
			//fileDelBtnContainer.insertAdjacentElement("beforebegin",img);
			fileDelBtn.style.display = '';
		};	
		changed.value="1"; //파일바뀜	
	}
	
	//js에서 삭제버튼 onclick -> 이미지클래스 src 지우고..
	//원본파일은 컨트롤러에서 조건처리(만약에 파일이 ''이면 원래파일삭제..원래파일은? 히든으로 전달할가..
	fileDelBtn.onclick = ()=>{
		img.src = "/images/icon/picture.png"
		img.classList.add("basic");
		fileInput.value="";
		changed.value="1";
		fileDelBtn.style.display = 'none';
		
	}
	
});



//input hidden 1 : 원래 파일 이름
//input hidden 2 : 변경여부
//변경안됐으면..업데이트할때 파일은 냅둠

window.addEventListener("load", ()=>{
	const fileDelBtn = document.querySelector(".file-del-btn");
	
	let img = document.querySelector(".image");
	
	if(img.classList.contains("basic")){ //기본사진일때
		console.log("dd");
		fileDelBtn.style.display = 'none';
		
	}else{
		fileDelBtn.style.display = '';
	}
})