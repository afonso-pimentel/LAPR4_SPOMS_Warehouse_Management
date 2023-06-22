



document.addEventListener("DOMContentLoaded", function () {

  const plant = {
    "Length": 800,
    "Width": 720,
    "Square": 40
  }

  let row1 = document.createElement("div");
  row1.classList.add("row");
  row1.classList.add("r1");
  row1.appendChild(drawLength(plant));

  let clear = document.createElement("div");
  clear.classList.add("cl_float");
  clear.setAttribute(
    "style",
    `clear:both;`
  );


  let row2 = document.createElement("div");
  row2.classList.add("row");
  row2.classList.add("r2");


  let colLeft = document.createElement("div");
  colLeft.classList.add("col-auto");
  
  colLeft.appendChild(drawPlant(plant));

  let colRight = document.createElement("div");
  colRight.classList.add("col-auto");
  colRight.appendChild(drawWidth(plant));

  row2.appendChild(colLeft);
  row2.appendChild(colRight);


  document.getElementById("center_plant").appendChild(row1);
  document.getElementById("center_plant").appendChild(clear);

  document.getElementById("center_plant").appendChild(row2);


  drawDocks();
  drawAisles();
  loadAndStart();
});



function drawFiller() {

  let filler = document.createElement("div");
  filler.classList.add("grid");
  filler.classList.add("filler");
  filler.setAttribute(
    "style",
    `grid-template-rows: repeat(2, 1fr); grid-template-columns: repeat(2, 1fr);`
  );


  for (let i = 1; i <= 2; i++) {
    for (let j = 1; j <= 2; j++) {

    let sqr = document.createElement("div");
    sqr.classList.add("grid_header_item");
    sqr.classList.add("clear");
    
    sqr.innerText = i;

    filler.append(sqr);
  }
}

  return filler;

}



function drawLength(plant) {
  let xLen = getPlantLength(plant);

  let lenBar = document.createElement("div");
  lenBar.classList.add("h_bar");
  lenBar.setAttribute("id", "length-bar");


  let header = document.createElement("div");
  header.classList.add("h_header");
  header.setAttribute("id", "len-header");

  header.innerText = "Length (in squares)";
  
  lenBar.append(header);



  for (let i = 1; i <= xLen; i++) {
    // let id = `len${("00" + i).slice(-2)}`;
    let id = `len${i}`;

    let sqr = document.createElement("div");
    sqr.classList.add("sqr");
    sqr.setAttribute("id", id);
    
    sqr.innerText = i;

    lenBar.append(sqr);
  }

  return lenBar;

}




function drawWidth(plant) {
  let yLen = getPlantWidth(plant);

  let widBar = document.createElement("div");
  widBar.classList.add("v_bar");
  widBar.classList.add("row");
  widBar.setAttribute("id", "width-bar");

  let sqrs = document.createElement("div");
  sqrs.classList.add("v_sqrs");
  sqrs.classList.add("col");

  for (let i = 1; i <= yLen; i++) {
    // let id = `wid${("00" + i).slice(-2)}`;
    let id = `wid${i}`;

    let sqr = document.createElement("div");
    sqr.classList.add("sqr");
    sqr.setAttribute("id", id);
    
    sqr.innerText = i;

    sqrs.append(sqr);
  }
  widBar.append(sqrs);


  let header = document.createElement("div");
  header.classList.add("v_header");
  header.classList.add("col");
  header.setAttribute("id", "wid-header");

  header.innerText = "Width (in squares)";
  
  widBar.append(header);


  return widBar;

}





function getPlantLength(plant) {
  let pSqr = plant.Square;
  let pSqrLen = plant.Length;
  let pLen = pSqrLen / pSqr;

  return pLen;
}

function getPlantWidth(plant) {
  let pSqr = plant.Square;
  let pSqrWid = plant.Width;
  let pWid = pSqrWid / pSqr;

  return pWid;
}





function drawPlant(plant) {
  let yLen = getPlantWidth(plant);
  let xLen = getPlantLength(plant);

  let warehouseSquare = document.createElement("div");
  warehouseSquare.classList.add("warehouse");
  warehouseSquare.setAttribute("id", "warehouse");

  
  for (let j = 1; j <= yLen; j++) {
      for (let i = 1; i <= xLen; i++) {

      // let id = `x${("00" + i).slice(-2)}y${("00" + j).slice(-2)}`;
      let id = `x${i}y${j}`;

      let sqr = document.createElement("div");
      sqr.classList.add("sqr");
      sqr.innerText = j + "/" + i;
      sqr.setAttribute("id", id);


      warehouseSquare.append(sqr);
    }
  }

  return warehouseSquare;
}


function drawDocks(){

  document.querySelector("#x1y3").classList.add("agv_dock");
  document.querySelector("#x1y5").classList.add("agv_dock");
  document.querySelector("#x1y13").classList.add("agv_dock");
  document.querySelector("#x1y15").classList.add("agv_dock");

  document.querySelector("#x20y4").classList.add("agv_dock");
  document.querySelector("#x20y14").classList.add("agv_dock");
}

function drawAisles(){

  document.querySelector("#x5y1").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x6y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x7y1").classList.add("b_clear", "tr_aisle", "br_aisle");


  document.querySelector("#x8y1").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x9y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x10y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x11y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x12y1").classList.add("b_clear", "tr_aisle", "br_aisle");

  document.querySelector("#x13y1").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x14y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x15y1").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x16y1").classList.add("b_clear", "tr_aisle", "br_aisle");



  document.querySelector("#x5y8").classList.add("b_clear", "tl_aisle");
  document.querySelector("#x6y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x7y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x8y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x9y8").classList.add("b_clear", "tr_aisle");

  document.querySelector("#x5y9").classList.add("b_clear", "bl_aisle");
  document.querySelector("#x6y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x7y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x8y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x9y9").classList.add("b_clear", "br_aisle");


  document.querySelector("#x10y8").classList.add("b_clear", "tl_aisle");
  document.querySelector("#x11y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x12y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x13y8").classList.add("b_clear", "tr_aisle");

  document.querySelector("#x10y9").classList.add("b_clear", "bl_aisle");
  document.querySelector("#x11y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x12y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x13y9").classList.add("b_clear", "br_aisle");


  document.querySelector("#x14y8").classList.add("b_clear", "tl_aisle");
  document.querySelector("#x15y8").classList.add("b_clear", "t_aisle");
  document.querySelector("#x16y8").classList.add("b_clear", "tr_aisle");

  document.querySelector("#x14y9").classList.add("b_clear", "bl_aisle");
  document.querySelector("#x15y9").classList.add("b_clear", "b_aisle");
  document.querySelector("#x16y9").classList.add("b_clear", "br_aisle");


  document.querySelector("#x5y10").classList.add("b_clear", "tl_aisle", "c_lorange");
  document.querySelector("#x6y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x7y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x8y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x9y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x10y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x11y10").classList.add("b_clear", "tr_aisle", "c_lorange");

  document.querySelector("#x5y11").classList.add("b_clear", "bl_aisle", "c_lorange");
  document.querySelector("#x6y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x7y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x8y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x9y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x10y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x11y11").classList.add("b_clear", "br_aisle", "c_lorange");


  
  document.querySelector("#x12y10").classList.add("b_clear", "tl_aisle", "c_lorange");
  document.querySelector("#x13y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x14y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x15y10").classList.add("b_clear", "t_aisle", "c_lorange");
  document.querySelector("#x16y10").classList.add("b_clear", "tr_aisle", "c_lorange");

  document.querySelector("#x12y11").classList.add("b_clear", "bl_aisle", "c_lorange");
  document.querySelector("#x13y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x14y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x15y11").classList.add("b_clear", "b_aisle", "c_lorange");
  document.querySelector("#x16y11").classList.add("b_clear", "br_aisle", "c_lorange");



  document.querySelector("#x5y18").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x6y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x7y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x8y18").classList.add("b_clear", "tr_aisle", "br_aisle");

  document.querySelector("#x9y18").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x10y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x11y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x12y18").classList.add("b_clear", "tr_aisle", "br_aisle");

  document.querySelector("#x13y18").classList.add("b_clear", "tl_aisle", "bl_aisle");
  document.querySelector("#x14y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x15y18").classList.add("b_clear", "t_aisle", "b_aisle");
  document.querySelector("#x16y18").classList.add("b_clear", "tr_aisle", "br_aisle");


  document.querySelector("#x13y13").classList.add("circle");

}