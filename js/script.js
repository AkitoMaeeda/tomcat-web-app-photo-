function updateSize(){
    console.log("関数のなか");
    let nBytes =0,oFiles=this.files,nFiles=oFiles.length;
    for (let nFileId = 0; nFileId<nFiles; nFileId++){
        nBytes += oFiles[nFileId].size;
        handleFiles(oFiles);
    }
    let sOutput = nBytes+"bytesだよ";

    const aMultiples =["Kbyte","Mbyte","Gbyte","Tbyte"];

    for(let nMultiples=0, nApprox = nBytes/1024; nApprox>1; nApprox/=1024, nMultiples++){
        sOutput = nApprox.toFixed(3)+""+aMultiples[nMultiples]+"("+nBytes+"bytes)";

    }

    document.getElementById("filenum").innerHTML = nFiles;
    document.getElementById("filesize").innerHTML = sOutput;
}
window.onload = function(){const fileSelector = document.getElementById("fileselect") 
fileElem = document.getElementById("uploadInput"),
preview = document.getElementById("preview");

fileSelector.addEventListener("click",function(e){
    if(fileElem){
        console.log("ifnonaka");
        fileElem.click();  
        e.preventDefault();
    }
},false);
fileElem.addEventListener("change",updateSize,false);
}

function handleFiles(files){
    console.log("関数2野中");
    for (let i = 0; i < files.length; i++){

        const file = files[i];
        console.log("for分の中");

        if(!file.type.startsWith('image/')){continue}
        console.log("イメージファイル");

        const img = document.createElement("img");
        img.classList.add("obj");
        img.file = file;
        preview.appendChild(img);

        const reader = new FileReader();
        reader.onload = (function(aImg){
            return function(e){ aImg.src = e.target.result;};})(img);
            reader.readAsDataURL(file);

    }
}
