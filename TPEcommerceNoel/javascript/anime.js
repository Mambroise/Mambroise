
    
var app = document.getElementById('app');

var typewriter = new Typewriter(app, {
    loop: true
});

typewriter.typeString("C'est <span style='color: red'><strong>Noel</strong> </span>, je peux maintenant m'amuser avec:<span style ='color: red'><strong> HTML</strong></span>")
    .pauseFor(2000)
    .deleteChars(4)
    .typeString("<span style ='color: red'><strong>CSS</strong></span>")
    .pauseFor(2000)
    .deleteChars(3)
    .typeString("<span style ='color: red'><strong>JQuery</strong></span>")
    .pauseFor(2000)
    .deleteChars(6)
    .typeString("<span style ='color: red'><strong>Ajax</strong></span>")
    .pauseFor(2000)
    .start();
