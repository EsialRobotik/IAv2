var jsonTable = {
  "tailleX": 3000,
  "tailleY": 2000,
  "couleur0": "Bleu",
  "couleur3000": "Vert",
  "marge": 190,
  "zonesInterdites": [
    {
      "id": "cherry_north",
      "forme": "polygone",
      "desc": "Cerise Nord",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 985
        },
        {
          "x": 0,
          "y": 1015
        },
        {
          "x": 300,
          "y": 1015
        },
        {
          "x": 300,
          "y": 985
        }
      ]
    },
    {
      "id": "cherry_south",
      "forme": "polygone",
      "desc": "Cerise Sud",
      "active": true,
      "points": [
        {
          "x": 3000,
          "y": 985
        },
        {
          "x": 3000,
          "y": 1015
        },
        {
          "x": 2700,
          "y": 1015
        },
        {
          "x": 2700,
          "y": 985
        }
      ]
    },
    {
      "id": "cherry_west",
      "forme": "polygone",
      "desc": "Cerise Ouest",
      "active": true,
      "points": [
        {
          "x": 1350,
          "y": 0
        },
        {
          "x": 1650,
          "y": 0
        },
        {
          "x": 1650,
          "y": 30
        },
        {
          "x": 1350,
          "y": 30
        }
      ]
    },
    {
      "id": "cherry_east",
      "forme": "polygone",
      "desc": "Cerise Est",
      "active": true,
      "points": [
        {
          "x": 1350,
          "y": 2000
        },
        {
          "x": 1650,
          "y": 2000
        },
        {
          "x": 1650,
          "y": 1970
        },
        {
          "x": 1350,
          "y": 1970
        }
      ]
    }
  ],
  "elementsJeu" : [
    {
      "id": "start3000_3",
      "forme": "polygone",
      "desc": "Depart Vert 3",
      "active": false,
      "points": [
        {
          "x": 930,
          "y": 0
        },
        {
          "x": 1320,
          "y": 0
        },
        {
          "x": 1320,
          "y": 420
        },
        {
          "x": 930,
          "y": 420
        }
      ]
    },
    {
      "id": "start0_3",
      "forme": "polygone",
      "desc": "Depart Bleu 3",
      "active": false,
      "points": [
        {
          "x": 930,
          "y": 2000
        },
        {
          "x": 1320,
          "y": 2000
        },
        {
          "x": 1320,
          "y": 1580
        },
        {
          "x": 930,
          "y": 1580
        }
      ]
    },

    {
      "id": "west_cake_purple_1",
      "forme" : "cercle",
      "desc": "Gateau violet ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 575,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_yellow_1",
      "forme" : "cercle",
      "desc": "Gateau jaune ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 775,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_brown_1",
      "forme" : "cercle",
      "desc": "Gateau brun ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 1125,
        "y" : 725
      },
      "rayon": 60
    },
    {
      "id": "west_cake_purple_2",
      "forme" : "cercle",
      "desc": "Gateau violet ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 2425,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_yellow_2",
      "forme" : "cercle",
      "desc": "Gateau jaune ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 2225,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_brown_2",
      "forme" : "cercle",
      "desc": "Gateau brun ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 1875,
        "y" : 725
      },
      "rayon": 60
    },

    {
      "id": "east_cake_purple_1",
      "forme" : "cercle",
      "desc": "Gateau violet est - 1",
      "active": true,
      "centre" :
      {
        "x" : 575,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_yellow_1",
      "forme" : "cercle",
      "desc": "Gateau jaune est - 1",
      "active": true,
      "centre" :
      {
        "x" : 775,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_brown_1",
      "forme" : "cercle",
      "desc": "Gateau brun est - 1",
      "active": true,
      "centre" :
      {
        "x" : 1125,
        "y" : 1275
      },
      "rayon": 60
    },
    {
      "id": "east_cake_purple_2",
      "forme" : "cercle",
      "desc": "Gateau violet est - 2",
      "active": true,
      "centre" :
      {
        "x" : 2425,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_yellow_2",
      "forme" : "cercle",
      "desc": "Gateau jaune est - 2",
      "active": true,
      "centre" :
      {
        "x" : 2225,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_brown_2",
      "forme" : "cercle",
      "desc": "Gateau brun est - 2",
      "active": true,
      "centre" :
      {
        "x" : 1875,
        "y" : 1275
      },
      "rayon": 60
    }
  ],
  "detectionIgnoreZone": [
  ]
};var strategyBig0 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 1775,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;1775","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"go#-30","position":{"x": 2460,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2460,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2460,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"go#50","position":{"x": 2410,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2410,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2410,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2410,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"goto#1200;1775","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"face#0;1775","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#8","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#9","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#2","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#7","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#0","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#11","position":{"x": 1200,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"goto-back#1400;1775","position":{"x": 1400,"y": 1775,"theta": -3.141592653589793}},{ "task":"homolo 2","command":"face#0;1775","position":{"x": 1400,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#8","position":{"x": 1400,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#9","position":{"x": 1400,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#2","position":{"x": 1400,"y": 1775,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"go#-100","position":{"x": 1500,"y": 1775,"theta": 3.141592653589793}}]
;var strategyBig3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 225,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;225","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"go#-30","position":{"x": 2460,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2460,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2460,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"go#50","position":{"x": 2410,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2410,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2410,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2410,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"goto#1200;225","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"face#0;225","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#8","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#9","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#2","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#7","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#0","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 1","command":"action#11","position":{"x": 1200,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"goto-back#1400;225","position":{"x": 1400,"y": 225,"theta": -3.141592653589793}},{ "task":"homolo 2","command":"face#0;225","position":{"x": 1400,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#8","position":{"x": 1400,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#9","position":{"x": 1400,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"action#2","position":{"x": 1400,"y": 225,"theta": 3.141592653589793}},{ "task":"homolo 2","command":"go#-100","position":{"x": 1500,"y": 225,"theta": 3.141592653589793}}]
;var strategySmall0 = [{ "task":"Position de départ","command":"start","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"On attends","command":"wait#0","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"Souffler premiere bouboule","command":"action#37","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"homolo","command":"go#-200","position":{"x": 2700,"y": 1775,"theta": 0.0}},{ "task":"homolo","command":"goto#2700;1200","position":{"x": 2700,"y": 1200,"theta": -1.5707963267948966}},{ "task":"homolo","command":"goto-back#2700;1700","position":{"x": 2700,"y": 1700,"theta": -1.5707963267948966}}]
;var strategySmall3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"On attends","command":"wait#0","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"Souffler premiere bouboule","command":"action#37","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"homolo","command":"go#-200","position":{"x": 2700,"y": 225,"theta": 0.0}},{ "task":"homolo","command":"goto#2700;800","position":{"x": 2700,"y": 800,"theta": 1.5707963267948966}},{ "task":"homolo","command":"goto-back#2700;300","position":{"x": 2700,"y": 300,"theta": -4.71238898038469}}]
;
