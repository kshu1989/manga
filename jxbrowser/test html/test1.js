function loaded(){
	var d = document.getElementById("d1");
	var p = document.createElement("img");
	p.src = "a.jpg";
	p.id = "a1";
	d.appendChild(p);
	
	var d = document.getElementById("d2");
	var p = document.createElement("img");
	p.id = "a2";
	p.src = "b.jpg";
	d.appendChild(p);
}