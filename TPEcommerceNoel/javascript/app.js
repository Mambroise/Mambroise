var produit = [
    {
      nom: 'Blouson Cuir Homme OSX',
      image: 'https://s1.rockagogostatic.com/ref/pls/pls15/blouson-cuir-mec-marque-osx-brando-jacket-pr.jpg',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 225
    },
    {
      nom: 'Polo cintre slimfit en coton Bleu',
      image: 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRjhMZbCnNOR5R5GDQYbqxxewrVHFKD5VtDZByreg8gr2xgfKmWhx9YgOr3joWoTCYA0jutSx3r5w&usqp=CAc',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 75
    },        
    {
      nom: 'Robe rose croisée à boucler',
      image: 'https://m1.quebecormedia.com/emp/emp/A1_2_1_d64e884e-d21e-41ab-8eb0-2baf6b656c00_ORIGINAL.jpg?impolicy=crop-resize&x=0&y=0&w=802&h=1086&width=925&height=925',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 50
    },
    {
      nom: 'Sneakers Adidas Original Homme',
      image: 'https://www.kiffoo.com/7220-large_default/basket-adidas-original-homme.jpg',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 159
    },
    {
      nom: 'Pantalon jogging Nike Just Do It - Noir',
      image: 'https://api.vs.prod.footkorner.nbs-aws.com/img/600/744/resize/catalog/product/f/o/footkorner-pantalon-nike-just-do-it-cu4050-010-noir_1_.jpeg',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 27
    },
    {
        nom: 'Sportwear Femme Gris',
        image: 'https://contents.mediadecathlon.com/p1691566/k$863fad91e6bb4a2de8373ca10dfc3a53/sq/sous-vetements-thermique.jpg?format=auto&f=800x0',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 20
    },
    {
        nom: 'Doudoune Rouge Homme',
      image: 'https://www.cdiscount.com/pdt2/2/7/7/1/700x700/mp40057277/rw/doudoune-rouge-homme-marque-duvet-de-canard-blanc.jpg',
      descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 60
    },
    {
        nom: 'UNDER ARMOUR BLITZING 3.0 MARINE',
        image: 'https://medias.go-sport.com/media/resized/340x/catalog/product/01/50/71/39/blitzing-30-marine_1_v1.jpg',
        descrption: 'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Non, laboriosam.',
      prix: 22
    }
];
var nbr = 0 //nbr article panier

var tableCart=[] // tableau du articles Panier
//========Crétaion des cartes Article============//
var card = ""
$(produit).each(function (i, value) {
    card += "<div class='card col-lg-2 col-md-6 col-sm-6'>"
    card += " <img src="+ value.image + " class='card-img-top'>"
    card += "<div class='card-body'>"
    card += "<p class='card-title text-center'><strong>"+value.nom+"</strong></p>"
    card += "<p class='card-text text-center'>"+value.descrption+"</p>"
    card += "<p class='card-text text-center'>Prix: "+value.prix+" €</p>"
    card += "<button id="+i+" class='btn btn-info add_to_cart'>Ajouter au panier</button>"
    card += "</div></div>"  
})
$(".container div").html(card)
//==============================Gestion articles panier================================================//
//=============================================================================================//
//========changement d'image du panier======
var emptyCart = function () {
    if (nbr>0) {
        var plein =  "<p>Voici la liste de vos produits. (vous pouvez en augmenter les quantités si vous le souhaitez)</p><div id='totalPrice'>"
            plein += "<strong>Total:<span class='cartPrice'>0 €</span></strong><button class='btn btn-primary' type='button' data-bs-toggle='offcanvas' data-bs-target='#staticBackdrop' aria-controls='staticBackdrop'>Valider mon panier</button></div>"
            $(".offcanvasHead").html(plein)
        } else if(nbr<=0){
            var vide = "<img src='img/emptyCart.jfif' alt='caddy vide'><p>Votre panier est tristement vide en ce moment</p>"
            $(".offcanvasHead").html(vide)
    }
}
// ajtout  article panier
$(".add_to_cart").on("click", function (e) {
    var exist = false
    var userChoice = $(this).attr("id");
    let artName = produit[userChoice].nom
    $(tableCart).each(function (i, value) {
        if (tableCart[i].nom === artName) {
            exist = true;
            tableCart[i].qty++
            console.log("doublon");
            changeQty();
        }
    })
    if (!exist) {
        tableCart[nbr] = { //tableau pour le total prix du panier
            nom: produit[userChoice].nom,
            prix: produit[userChoice].prix,
            qty: 1
        };
        nbr++  
    }
    badgeset();
    emptyCart();
});
// MAJ quantité article  //
function changeQty(value) {
   $(tableCart).each(function (i,prod) {
        if (prod.nom===$("#des"+value).text().trim()) {  
            tableCart[i].qty =$("#qty"+value).val()
        }
   })
    totalCart()
    badgeset()
};

function createCart() {
    $(".itemLocation").empty();
    for (let index = 0; index < tableCart.length; index++) {
        var userChoice$ = "<div class='cancel"+index+" cartChoice' ><div class='cartDesi'><p>Désignation:<strong id=des"+index+"> "+tableCart[index].nom+"</strong></p>"
        userChoice$ += "<button id='"+index+"' class='btn btn-danger cancelBtn' onclick='suppress("+index+")'>supprimer</button></div><div><label>Quantité: <input id='qty"+index+"'onchange='changeQty("+index+")'type='number' value='"+tableCart[index].qty+"' > "
        userChoice$ += "<p>prix: <strong> " +tableCart[index].prix+" €</strong></p></div>";
        $(".itemLocation").append(userChoice$);
    }
    totalCart()
}
function suppress(value) {
    let skip = $("#des"+value).text().trim()
    console.log(skip);
    $(tableCart).each(function (i,prod) {
        if (prod.nom === skip) {
            $(".cancel"+value).remove();
            tableCart.splice(i, 1);
        }
    });
    nbr--
    emptyCart();
    totalCart();
    badgeset();
}
// Gestion du Badge nombre articles panier
function badgeset() {
   var badge = 0
    for (let index = 0; index < tableCart.length; index++) {
        badge += parseInt(tableCart[index].qty);
    }
    $(".badge").html(badge);
}


//creation article du modal récap 
$("button#submit").on("click", function () {
    $.each(tableCart, function (i, value) {
        var recapArt = "<div class='recapRow'><p><strong>"+tableCart[i].nom+"</strong></p><span>Prix : "+tableCart[i].prix+" €  </span><span>Quantité : "+tableCart[i].qty+"</span></div>"
        $("#recapArt").append(recapArt)
    });
});
$(".recapClose").on("click", function () {
    $("#recapArt").empty()
})
//==Fonction Calcul panier//
var totalCart = function () {
var sum = 0 //Variable du total panier
$(tableCart).each(function (index, value) {
    sum += value.prix*value.qty
});
$(".cartPrice").html(" "+sum+" €");
}
// ==================================Gestion du Formulaire==========================================//
// =================================================================================================//
$( "#submit" ).prop( "disabled", true )

$("form input").on("keyup", function () {
    var inputName = $(this).attr("name")
    var saisie = $(this).val()
    var regExp = new RegExp()
    switch (inputName) {
            case "prenom":
        regExp = /^[a-zA-Z0-9]{3,15}$/;
        comment = "Votre "+inputName+" doit comprter entre 3 et 15 caractères"
        break;
            case "nom":
        regExp = /^[a-zA-Z0-9]{3,15}$/;
        comment = "Votre "+inputName+" doit comprter entre 3 et 15 caractères"
        break;

            case "email":
        regExp = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,3})+$/;
        comment = "Le format de votre email ne correspond pas"
        break;
            case "tel":
        regExp = /^(\\+33|0|0033)[1-9][0-9]{8}$/;
        comment = "Votre numéro ne correspond pas aux saisies françaises"
        break;
        case "adresse":
        regExp = /^[a-zA-Z0-9\ \-\,\:\.]{10,50}$/;
        comment = "Votre "+inputName+" doit comprter entre 10 et 50 caractères"
        break;
        default:
        break;
        }
        if (regExp.test(saisie)) {//test des regexp
                $($(this)).removeClass("wrong").addClass("right")
                $("#error"+inputName).html("")
                $("#recap"+inputName).html(saisie) //envoi du champ au recap
                $( "#submit" ).prop( "disabled", false )
            } else {
                $("#error"+inputName).html(comment)
                $($(this)).addClass("wrong")
                $( "#submit" ).prop( "disabled", true )
            } 
        $("form input").each(function (i, value) { //condition pour que qu'aucun input reste vide
            if (value.value == ""){
                $( "#submit" ).prop( "disabled", true )
            }
        })   
});
//=============================================RECAP============================//
$("#final").on("click", function () {
    $.ajax({
        url : "bravo.html",
        success: function (data) {
            console.log("good");
            $("body").html(data)
        }
    })
})
     
