window.addEventListener("load", ()=>{
         
         let emotionNum = document.querySelector(".emotion-type");
         let emotionType = emotionNum.value;
         console.log(emotionType);
         
         let section = document.querySelector("#tab");
         let location = section.querySelector(".upset").offsetTop;
         console.log(location);
         window.scrollTo({top:location, behavior:'smooth'});
         
         
         switch(parseInt(emotionType)){
            case 1 : 
               let happy = document.querySelector(".happy");
               happy.style.width = "80px";
               happy.style.borderBottom = "3px solid";
               
               break;
            case 2 : 
            	let proud = document.querySelector(".proud");
            	proud.style.width = "80px";
            	proud.style.borderBottom = "3px solid";
               
               break;
               
            case 3 : 
            	let flutter = document.querySelector(".flutter");
            	flutter.style.width = "80px"
            		flutter.style.borderBottom = "3px solid";
               break;
               
            case 4 : 
            	let calm = document.querySelector(".calm");
            	calm.style.width = "80px"
            		calm.style.borderBottom = "3px solid";
               break;
               
            case 5 : 
            	let sensitive = document.querySelector(".sensitive");
            	sensitive.style.width = "80px"
            		sensitive.style.borderBottom = "3px solid";
               break;
               
            case 6 : 
            	let unstable = document.querySelector(".unstable");
            	unstable.style.width = "80px"
            		unstable.style.borderBottom = "3px solid";
               break;
            
            case 7 :
            	let tired = document.querySelector(".tired");
            	tired.style.width = "80px"
            		tired.style.borderBottom = "3px solid";
            	break;
            	
			case 8 :
				let sad = document.querySelector(".sad");
				sad.style.width = "80px"
					sad.style.borderBottom = "3px solid";
            	break;
            	
			case 9 :
				let upset = document.querySelector(".upset");
				upset.style.width = "80px"
					upset.style.borderBottom = "3px solid";
				break;            	 	
         }     
         
      });
      
      
      