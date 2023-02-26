// alert("ok")
// confirm("CDA)")
// prompt("Donne ton nom:")
// var nombre = prompt("Donne ton nom:")
// console.log(nombre);
var age = 18
if (age <18) {
    alert("mineur!!")
} else {
   var answer = prompt("que veux tu mater?" )
}
// if autre maniere
switch (age) {
    case 12:
        console.log("no fuck");
        break;
    case 15:
        console.log("no fuck");
        break;
    case 16:
    case 17:
        console.log("legaly possible fuck");
        break;

    default:
        console.log("touche pas par précaution");
        break;
}
switch (true) {
    case age <= 12:
        console.log("no fuck");
        break;
    case age > 12 && age < 16:
        console.log("no fuck");
        break;
    case age > 16:
        console.log("legaly possible fuck");
        break;

    default:
        console.log("touche pas par précaution");
        break;
}
//Ternaires*********
if (condition) {
    process = 'ok'
} else {
    
}
process = condition ? "ok ! " : "failed ! "

//  tableaux
var prenom =["Moussa", "Moris", 15, "Laura"]
console.log(prenom[3]);

for (let index = 0; index < array.length; index++) {
    console.log("index: "+ index+ " =>"+ prenom[index]);
    if (prenom[index] === 15) {
        prenom[index] = "dujardin"
    } 
};
console.log("\t"+ prenom);

table[
    ["moussa", 15]
    ["mo", 9]
    ["laura", 20]
];

for (let index = 0; index < table.length; index++) {
    for (let j = 0; j < table.length; j++) {
        const element = table[j];
        
    }
    
};

// ***************Foreach

table.forEach(element => {
    console.log("ForEach affichage de l'element "+ element);
    element.forEach(el => {
            console.log("ForEach affichage de l'element "+ el);
        
    });
});