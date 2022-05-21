var jsonTable = {
  "tailleX": 2000,
  "tailleY": 3000,
  "couleur0": "Jaune",
  "couleur3000": "Violet",
  "marge": 170,
  "zonesInterdites": [
    {
      "id": "start0",
      "forme": "polygone",
      "desc": "Depart Jaune",
      "active": true,
      "points": [
        {
          "x": 400,
          "y": 0
        },
        {
          "x": 400,
          "y": 400
        },
        {
          "x": 1000,
          "y": 400
        },
        {
          "x": 1000,
          "y": 0
        }
      ]
    },
    {
      "id": "start3000",
      "forme": "polygone",
      "desc": "Depart Violet",
      "active": true,
      "points": [
        {
          "x": 400,
          "y": 3000
        },
        {
          "x": 400,
          "y": 2600
        },
        {
          "x": 1000,
          "y": 2600
        },
        {
          "x": 1000,
          "y": 3000
        }
      ]
    },
    {
      "id": "yellowDistributor",
      "forme" : "polygone",
      "desc": "Distributeur jaune",
      "active": true,
      "points": [
        {
          "x": 1175,
          "y": 0
        },
        {
          "x": 1175,
          "y": 102
        },
        {
          "x": 1325,
          "y": 102
        },
        {
          "x": 1325,
          "y": 0
        }
      ]
    },
    {
      "id": "purpleDistributor",
      "forme" : "polygone",
      "desc": "Distributeur violet",
      "active": true,
      "points": [
        {
          "x": 1175,
          "y": 3000
        },
        {
          "x": 1175,
          "y": 2898
        },
        {
          "x": 1325,
          "y": 2898
        },
        {
          "x": 1325,
          "y": 3000
        }
      ]
    },
    {
      "id": "commonYellowDistributor",
      "forme" : "polygone",
      "desc": "Distributeur commun jaune",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 1275
        },
        {
          "x": 102,
          "y": 1275
        },
        {
          "x": 102,
          "y": 1425
        },
        {
          "x": 0,
          "y": 1425
        }
      ]
    },
    {
      "id": "commonPurpleDistributor",
      "forme" : "polygone",
      "desc": "Distributeur commun violet",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 1575
        },
        {
          "x": 102,
          "y": 1575
        },
        {
          "x": 102,
          "y": 1725
        },
        {
          "x": 0,
          "y": 1725
        }
      ]
    },
    {
      "id": "commonDistributorSeparator",
      "forme" : "polygone",
      "desc": "Séparateur distributeur commun",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 1489
        },
        {
          "x": 300,
          "y": 1489
        },
        {
          "x": 300,
          "y": 1511
        },
        {
          "x": 0,
          "y": 1511
        }
      ]
    },
    {
      "id": "yellowSearchZone",
      "forme" : "polygone",
      "desc": "Site de fouille jaune",
      "active": true,
      "points": [
        {
          "x": 1200,
          "y": 800
        },
        {
          "x": 1200,
          "y": 1150
        },
        {
          "x": 1550,
          "y": 1150
        },
        {
          "x": 1550,
          "y": 800
        }
      ]
    },
    {
      "id": "purpleSearchZone",
      "forme" : "polygone",
      "desc": "Site de fouille violet",
      "active": true,
      "points": [
        {
          "x": 1200,
          "y": 1850
        },
        {
          "x": 1200,
          "y": 2200
        },
        {
          "x": 1550,
          "y": 2200
        },
        {
          "x": 1550,
          "y": 1850
        }
      ]
    },
    {
      "id": "yellowExposition",
      "forme" : "polygone",
      "desc": "Exposition jaune",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 450
        },
        {
          "x": 0,
          "y": 1170
        },
        {
          "x": 85,
          "y": 1170
        },
        {
          "x": 85,
          "y": 450
        }
      ]
    },
    {
      "id": "purpleExposition",
      "forme" : "polygone",
      "desc": "Exposition violet",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 1830
        },
        {
          "x": 0,
          "y": 2550
        },
        {
          "x": 85,
          "y": 2550
        },
        {
          "x": 85,
          "y": 1830
        }
      ]
    },
    {
      "id": "yellowWorkingShed",
      "forme" : "polygone",
      "desc": "Abris de chantier jaune",
      "active": true,
      "points": [
        {
          "x": 1490,
          "y": 0
        },
        {
          "x": 2000,
          "y": 510
        },
        {
          "x": 2000,
          "y": 0
        }
      ]
    },
    {
      "id": "yellowWorkingShed_margin",
      "forme" : "polygone",
      "desc": "Abris de chantier jaune",
      "active": true,
      "points": [
        {
          "x": 1310,
          "y": 0
        },
        {
          "x": 2000,
          "y": 690
        },
        {
          "x": 2000,
          "y": 0
        }
      ]
    },
    {
      "id": "purpleWorkingShed",
      "forme" : "polygone",
      "desc": "Abris de chantier violet",
      "active": true,
      "points": [
        {
          "x": 1490,
          "y": 3000
        },
        {
          "x": 2000,
          "y": 2490
        },
        {
          "x": 2000,
          "y": 3000
        }
      ]
    },
    {
      "id": "purpleWorkingShed_margin",
      "forme" : "polygone",
      "desc": "Abris de chantier violet",
      "active": true,
      "points": [
        {
          "x": 1310,
          "y": 3000
        },
        {
          "x": 2000,
          "y": 2310
        },
        {
          "x": 2000,
          "y": 3000
        }
      ]
    }
  ],
  "elementsJeu" : [
    {
      "id": "0_SampleBlue",
      "forme" : "cercle",
      "desc": "Echantillon bleu 0",
      "active": true,
      "centre" :
      {
        "x" : 555,
        "y" : 900
      },
      "rayon": 75
    },
    {
      "id": "0_SampleGreen",
      "forme" : "cercle",
      "desc": "Echantillon vert 0",
      "active": true,
      "centre" :
      {
        "x" : 675,
        "y" : 830
      },
      "rayon": 75
    },
    {
      "id": "0_SampleRed",
      "forme" : "cercle",
      "desc": "Echantillon rouge 0",
      "active": true,
      "centre" :
      {
        "x" : 795,
        "y" : 900
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleBlue",
      "forme" : "cercle",
      "desc": "Echantillon bleu 3000",
      "active": true,
      "centre" :
      {
        "x" : 555,
        "y" : 2100
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleGreen",
      "forme" : "cercle",
      "desc": "Echantillon vert 3000",
      "active": true,
      "centre" :
      {
        "x" : 675,
        "y" : 2170
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleRed",
      "forme" : "cercle",
      "desc": "Echantillon rouge 3000",
      "active": true,
      "centre" :
      {
        "x" : 795,
        "y" : 2100
      },
      "rayon": 75
    },

    {
      "id": "0_SampleCentralRed",
      "forme" : "cercle",
      "desc": "Echantillon central déposé rouge 0",
      "active": false,
      "centre" :
      {
        "x" : 960,
        "y" : 80
      },
      "rayon": 75
    },
    {
      "id": "0_SampleCentralGreen",
      "forme" : "cercle",
      "desc": "Echantillon central déposé vert 0",
      "active": false,
      "centre" :
      {
        "x" : 960,
        "y" : 230
      },
      "rayon": 75
    },
    {
      "id": "0_SampleCentralBlue",
      "forme" : "cercle",
      "desc": "Echantillon central déposé bleu 0",
      "active": false,
      "centre" :
      {
        "x" : 960,
        "y" : 380
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleCentralRed",
      "forme" : "cercle",
      "desc": "Echantillon central déposé rouge 3000",
      "active": false,
      "centre" :
      {
        "x" : 790,
        "y" : 2920
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleCentralGreen",
      "forme" : "cercle",
      "desc": "Echantillon central déposé vert 3000",
      "active": false,
      "centre" :
      {
        "x" : 790,
        "y" : 2770
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleCentralBlue",
      "forme" : "cercle",
      "desc": "Echantillon central déposé bleu 3000",
      "active": false,
      "centre" :
      {
        "x" : 790,
        "y" : 2620
      },
      "rayon": 75
    },

    {
      "id": "0_SampleLateralRed",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé rouge 0",
      "active": false,
      "centre" :
      {
        "x" : 780,
        "y" : 80
      },
      "rayon": 75
    },
    {
      "id": "0_SampleLateralGreen",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé vert 0",
      "active": false,
      "centre" :
      {
        "x" : 780,
        "y" : 230
      },
      "rayon": 75
    },
    {
      "id": "0_SampleLateralBlue",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé bleu 0",
      "active": false,
      "centre" :
      {
        "x" : 780,
        "y" : 380
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleLateralRed",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé rouge 3000",
      "active": false,
      "centre" :
      {
        "x" : 970,
        "y" : 2920
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleLateralGreen",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé vert 3000",
      "active": false,
      "centre" :
      {
        "x" : 970,
        "y" : 2770
      },
      "rayon": 75
    },
    {
      "id": "3000_SampleLateralBlue",
      "forme" : "cercle",
      "desc": "Echantillon Lateral déposé bleu 3000",
      "active": false,
      "centre" :
      {
        "x" : 970,
        "y" : 2620
      },
      "rayon": 75
    }
  ],
  "detectionIgnoreZone": [
  ]
};var strategyBig0 = [{ "task":"Position de départ","command":"start","position":{"x": 600,"y": 200,"theta": 1.5707963267948966}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 600,"y": 201,"theta": 1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#600;200","position":{"x": 600,"y": 200,"theta": -1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#300;500","position":{"x": 300,"y": 500,"theta": 2.356194490192345}},{ "task":"Placement distributeur central","command":"goto-astar#300;1290","position":{"x": 300,"y": 1290,"theta": 1.5707963267948966}},{ "task":"Placement distributeur central","command":"face#0;1290","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Preparer ramassage distributeur central","command":"action#2","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"goto#230;1290","position":{"x": 230,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"go#30","position":{"x": 200,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"action#3","position":{"x": 200,"y": 1290,"theta": 3.141592653589793}},{ "task":"Sortie distributeur central","command":"go#-100","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Placement campement","command":"goto-astar#300;1290","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Placement campement","command":"goto-astar#300;610","position":{"x": 300,"y": 610,"theta": -1.5707963267948966}},{ "task":"Placement campement","command":"goto-astar#440;470","position":{"x": 440,"y": 470,"theta": -0.7853981633974483}},{ "task":"Placement campement","command":"goto-astar#870;470","position":{"x": 870,"y": 470,"theta": 0.0}},{ "task":"Placement campement","command":"goto-astar#890;450","position":{"x": 890,"y": 450,"theta": -0.7853981633974483}},{ "task":"Placement campement","command":"goto-astar#900;450","position":{"x": 900,"y": 450,"theta": 0.0}},{ "task":"Placement campement largage rouge","command":"goto#900;300","position":{"x": 900,"y": 300,"theta": -1.5707963267948966}},{ "task":"Largage campement rouge","command":"action#4","position":{"x": 900,"y": 300,"theta": -1.5707963267948966}},{ "task":"Echantillon central déposé rouge","command":"add-zone#0_SampleCentralRed","position":{"x": 900,"y": 300,"theta": -1.5707963267948966}},{ "task":"Placement campement largage vert","command":"goto-back#900;450","position":{"x": 900,"y": 450,"theta": -1.5707963267948966}},{ "task":"Largage campement vert","command":"action#5","position":{"x": 900,"y": 450,"theta": -1.5707963267948966}},{ "task":"Echantillon central déposé vert","command":"add-zone#0_SampleCentralGreen","position":{"x": 900,"y": 450,"theta": -1.5707963267948966}},{ "task":"Placement campement largage bleu","command":"goto-back#900;600","position":{"x": 900,"y": 600,"theta": -1.5707963267948966}},{ "task":"Largage campement bleu","command":"action#6","position":{"x": 900,"y": 600,"theta": -1.5707963267948966}},{ "task":"Echantillon central déposé bleu","command":"add-zone#0_SampleCentralBlue","position":{"x": 900,"y": 600,"theta": -1.5707963267948966}},{ "task":"Sortie campement","command":"goto-back#900;650","position":{"x": 900,"y": 650,"theta": -1.5707963267948966}},{ "task":"Placement distributeur latéral","command":"goto-astar#900;650","position":{"x": 900,"y": 650,"theta": -1.5707963267948966}},{ "task":"Placement distributeur latéral","command":"goto-astar#920;630","position":{"x": 920,"y": 630,"theta": -0.7853981633974483}},{ "task":"Placement distributeur latéral","command":"goto-astar#1130;630","position":{"x": 1130,"y": 630,"theta": 0.0}},{ "task":"Placement distributeur latéral","command":"goto-astar#1300;460","position":{"x": 1300,"y": 460,"theta": -0.7853981633974483}},{ "task":"Placement distributeur latéral","command":"goto-astar#1300;400","position":{"x": 1300,"y": 400,"theta": -1.5707963267948966}},{ "task":"Alignement distributeur latéral","command":"face#1300;0","position":{"x": 1300,"y": 400,"theta": -1.5707963267948966}},{ "task":"Preparer ramassage distributeur latéral","command":"action#2","position":{"x": 1300,"y": 400,"theta": -1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"goto#1300;230","position":{"x": 1300,"y": 230,"theta": -1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"go#30","position":{"x": 1300,"y": 200,"theta": -1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"action#3","position":{"x": 1300,"y": 200,"theta": -1.5707963267948966}},{ "task":"Sortie distributeur latéral","command":"go#-200","position":{"x": 1300,"y": 400,"theta": -1.5707963267948966}},{ "task":"Placement campement","command":"goto#1200;600","position":{"x": 1200,"y": 600,"theta": 2.0344439357957027}},{ "task":"Placement campement","command":"goto#720;600","position":{"x": 720,"y": 600,"theta": 3.141592653589793}},{ "task":"Placement campement largage rouge","command":"goto#720;300","position":{"x": 720,"y": 300,"theta": -1.5707963267948966}},{ "task":"Largage campement rouge","command":"action#4","position":{"x": 720,"y": 300,"theta": -1.5707963267948966}},{ "task":"Echantillon Lateral déposé rouge","command":"add-zone#0_SampleLateralRed","position":{"x": 720,"y": 300,"theta": -1.5707963267948966}},{ "task":"Placement campement largage vert","command":"goto-back#720;450","position":{"x": 720,"y": 450,"theta": -1.5707963267948966}},{ "task":"Largage campement vert","command":"action#5","position":{"x": 720,"y": 450,"theta": -1.5707963267948966}},{ "task":"Echantillon Lateral déposé vert","command":"add-zone#0_SampleLateralGreen","position":{"x": 720,"y": 450,"theta": -1.5707963267948966}},{ "task":"Placement campement largage bleu","command":"goto-back#720;600","position":{"x": 720,"y": 600,"theta": -1.5707963267948966}},{ "task":"Largage campement bleu","command":"action#6","position":{"x": 720,"y": 600,"theta": -1.5707963267948966}},{ "task":"Echantillon Lateral déposé bleu","command":"add-zone#0_SampleLateralBlue","position":{"x": 720,"y": 600,"theta": -1.5707963267948966}},{ "task":"Sortie campement","command":"goto-back#720;630","position":{"x": 720,"y": 630,"theta": -1.5707963267948966}},{ "task":"Sortie campement","command":"goto#900;650","position":{"x": 900,"y": 650,"theta": 0.11065722117389563}},{ "task":"Placemnt Carré 1","command":"goto-astar#900;650","position":{"x": 900,"y": 650,"theta": 0.11065722117389563}},{ "task":"Placemnt Carré 1","command":"goto-astar#1090;650","position":{"x": 1090,"y": 650,"theta": 0.0}},{ "task":"Placemnt Carré 1","command":"goto-astar#1120;620","position":{"x": 1120,"y": 620,"theta": -0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1650;620","position":{"x": 1650,"y": 620,"theta": 0.0}},{ "task":"Placemnt Carré 1","command":"goto-astar#1700;670","position":{"x": 1700,"y": 670,"theta": 0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1750;670","position":{"x": 1750,"y": 670,"theta": 0.0}},{ "task":"Alignement Carré 1","command":"face#1750;3000","position":{"x": 1750,"y": 670,"theta": 1.5707963267948966}},{ "task":"Carré 1","command":"action#7","position":{"x": 1750,"y": 670,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 2","command":"goto#1750;850","position":{"x": 1750,"y": 850,"theta": 1.5707963267948966}},{ "task":"Carré 2","command":"action#7","position":{"x": 1750,"y": 850,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 3","command":"goto#1750;1040","position":{"x": 1750,"y": 1040,"theta": 1.5707963267948966}},{ "task":"Carré 3","command":"action#7","position":{"x": 1750,"y": 1040,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 4","command":"goto#1750;1220","position":{"x": 1750,"y": 1220,"theta": 1.5707963267948966}},{ "task":"Carré 4","command":"action#7","position":{"x": 1750,"y": 1220,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 5","command":"goto#1750;1410","position":{"x": 1750,"y": 1410,"theta": 1.5707963267948966}},{ "task":"Carré 5","command":"action#7","position":{"x": 1750,"y": 1410,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 6","command":"goto#1750;1590","position":{"x": 1750,"y": 1590,"theta": 1.5707963267948966}},{ "task":"Carré 6","command":"action#7","position":{"x": 1750,"y": 1590,"theta": 1.5707963267948966}},{ "task":"Placemnt Carré 7","command":"goto#1750;1780","position":{"x": 1750,"y": 1780,"theta": 1.5707963267948966}},{ "task":"Carré 7","command":"action#7","position":{"x": 1750,"y": 1780,"theta": 1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1750;1780","position":{"x": 1750,"y": 1780,"theta": 1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1700;1730","position":{"x": 1700,"y": 1730,"theta": 3.9269908169872414}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1700;1400","position":{"x": 1700,"y": 1400,"theta": -1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1650;1350","position":{"x": 1650,"y": 1350,"theta": 3.9269908169872414}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1600;1350","position":{"x": 1600,"y": 1350,"theta": 3.141592653589793}},{ "task":"Mise en place rangement zone de fouille","command":"goto#1600;1000","position":{"x": 1600,"y": 1000,"theta": -1.5707963267948966}}]
;var strategyBig3000 = [{ "task":"Position de départ","command":"start","position":{"x": 600,"y": 2800,"theta": -1.5707963267948966}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 600,"y": 2799,"theta": -1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#600;2790","position":{"x": 600,"y": 2790,"theta": -1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#300;2490","position":{"x": 300,"y": 2490,"theta": 3.9269908169872414}},{ "task":"Placement distributeur central","command":"goto-astar#300;1800","position":{"x": 300,"y": 1800,"theta": -1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#490;1610","position":{"x": 490,"y": 1610,"theta": -0.7853981633974483}},{ "task":"Placement distributeur central","command":"goto-astar#490;1380","position":{"x": 490,"y": 1380,"theta": -1.5707963267948966}},{ "task":"Placement distributeur central","command":"goto-astar#400;1290","position":{"x": 400,"y": 1290,"theta": 3.9269908169872414}},{ "task":"Placement distributeur central","command":"goto-astar#300;1290","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Placement distributeur central","command":"face#0;1290","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Preparer ramassage distributeur central","command":"action#2","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"goto#230;1290","position":{"x": 230,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"go#30","position":{"x": 200,"y": 1290,"theta": 3.141592653589793}},{ "task":"Ramassage distributeur central","command":"action#3","position":{"x": 200,"y": 1290,"theta": 3.141592653589793}},{ "task":"Sortie distributeur central","command":"go#-100","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Placement campement","command":"goto-astar#300;1290","position":{"x": 300,"y": 1290,"theta": 3.141592653589793}},{ "task":"Placement campement","command":"goto-astar#310;1300","position":{"x": 310,"y": 1300,"theta": 0.7853981633974483}},{ "task":"Placement campement","command":"goto-astar#430;1300","position":{"x": 430,"y": 1300,"theta": 0.0}},{ "task":"Placement campement","command":"goto-astar#500;1370","position":{"x": 500,"y": 1370,"theta": 0.7853981633974483}},{ "task":"Placement campement","command":"goto-astar#500;1760","position":{"x": 500,"y": 1760,"theta": 1.5707963267948966}},{ "task":"Placement campement","command":"goto-astar#330;1930","position":{"x": 330,"y": 1930,"theta": 2.356194490192345}},{ "task":"Placement campement","command":"goto-astar#330;1950","position":{"x": 330,"y": 1950,"theta": 1.5707963267948966}},{ "task":"Placement campement","command":"goto-astar#300;1980","position":{"x": 300,"y": 1980,"theta": 2.356194490192345}},{ "task":"Placement campement","command":"goto-astar#300;2230","position":{"x": 300,"y": 2230,"theta": 1.5707963267948966}},{ "task":"Placement campement","command":"goto-astar#620;2550","position":{"x": 620,"y": 2550,"theta": 0.7853981633974483}},{ "task":"Placement campement","command":"goto-astar#850;2550","position":{"x": 850,"y": 2550,"theta": 0.0}},{ "task":"Placement campement largage rouge","command":"goto#850;2700","position":{"x": 850,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Largage campement rouge","command":"action#4","position":{"x": 850,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Echantillon central déposé rouge","command":"add-zone#3000_SampleCentralRed","position":{"x": 850,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Placement campement largage vert","command":"goto-back#850;2550","position":{"x": 850,"y": 2550,"theta": -4.71238898038469}},{ "task":"Largage campement vert","command":"action#5","position":{"x": 850,"y": 2550,"theta": -4.71238898038469}},{ "task":"Echantillon central déposé vert","command":"add-zone#3000_SampleCentralGreen","position":{"x": 850,"y": 2550,"theta": -4.71238898038469}},{ "task":"Placement campement largage bleu","command":"goto-back#850;2400","position":{"x": 850,"y": 2400,"theta": -4.71238898038469}},{ "task":"Largage campement bleu","command":"action#6","position":{"x": 850,"y": 2400,"theta": -4.71238898038469}},{ "task":"Echantillon central déposé bleu","command":"add-zone#3000_SampleCentralBlue","position":{"x": 850,"y": 2400,"theta": -4.71238898038469}},{ "task":"Sortie campement","command":"goto-back#850;2350","position":{"x": 850,"y": 2350,"theta": -4.71238898038469}},{ "task":"Placement distributeur latéral","command":"goto-astar#850;2350","position":{"x": 850,"y": 2350,"theta": -4.71238898038469}},{ "task":"Placement distributeur latéral","command":"goto-astar#860;2360","position":{"x": 860,"y": 2360,"theta": 0.7853981633974483}},{ "task":"Placement distributeur latéral","command":"goto-astar#950;2360","position":{"x": 950,"y": 2360,"theta": 0.0}},{ "task":"Placement distributeur latéral","command":"goto-astar#1190;2600","position":{"x": 1190,"y": 2600,"theta": 0.7853981633974483}},{ "task":"Placement distributeur latéral","command":"goto-astar#1200;2600","position":{"x": 1200,"y": 2600,"theta": 0.0}},{ "task":"Alignement distributeur latéral","command":"face#1200;3000","position":{"x": 1200,"y": 2600,"theta": 1.5707963267948966}},{ "task":"Preparer ramassage distributeur latéral","command":"action#2","position":{"x": 1200,"y": 2600,"theta": 1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"goto#1200;2770","position":{"x": 1200,"y": 2770,"theta": 1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"go#30","position":{"x": 1200,"y": 2800,"theta": 1.5707963267948966}},{ "task":"Ramassage distributeur latéral","command":"action#3","position":{"x": 1200,"y": 2800,"theta": 1.5707963267948966}},{ "task":"Sortie distributeur latéral","command":"go#-200","position":{"x": 1200,"y": 2600,"theta": 1.5707963267948966}},{ "task":"Placement campement","command":"goto#1200;2400","position":{"x": 1200,"y": 2400,"theta": -1.5707963267948966}},{ "task":"Placement campement","command":"goto#1030;2400","position":{"x": 1030,"y": 2400,"theta": 3.141592653589793}},{ "task":"Placement campement largage rouge","command":"goto#1030;2700","position":{"x": 1030,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Largage campement rouge","command":"action#4","position":{"x": 1030,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Echantillon Lateral déposé rouge","command":"add-zone#3000_SampleLateralRed","position":{"x": 1030,"y": 2700,"theta": 1.5707963267948966}},{ "task":"Placement campement largage vert","command":"goto-back#1030;2550","position":{"x": 1030,"y": 2550,"theta": -4.71238898038469}},{ "task":"Largage campement vert","command":"action#5","position":{"x": 1030,"y": 2550,"theta": -4.71238898038469}},{ "task":"Echantillon Lateral déposé vert","command":"add-zone#3000_SampleLateralGreen","position":{"x": 1030,"y": 2550,"theta": -4.71238898038469}},{ "task":"Placement campement largage bleu","command":"goto-back#1030;2400","position":{"x": 1030,"y": 2400,"theta": -4.71238898038469}},{ "task":"Largage campement bleu","command":"action#6","position":{"x": 1030,"y": 2400,"theta": -4.71238898038469}},{ "task":"Echantillon Lateral déposé bleu","command":"add-zone#3000_SampleLateralBlue","position":{"x": 1030,"y": 2400,"theta": -4.71238898038469}},{ "task":"Sortie campement","command":"goto-back#1030;2370","position":{"x": 1030,"y": 2370,"theta": -4.71238898038469}},{ "task":"Sortie campement","command":"goto#1030;2370","position":{"x": 1030,"y": 2370,"theta": 3.141592653589793}},{ "task":"Placemnt Carré 1","command":"goto-astar#1030;2370","position":{"x": 1030,"y": 2370,"theta": 3.141592653589793}},{ "task":"Placemnt Carré 1","command":"goto-astar#1040;2360","position":{"x": 1040,"y": 2360,"theta": -0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1090;2360","position":{"x": 1090,"y": 2360,"theta": 0.0}},{ "task":"Placemnt Carré 1","command":"goto-astar#1120;2390","position":{"x": 1120,"y": 2390,"theta": 0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1640;2390","position":{"x": 1640,"y": 2390,"theta": 0.0}},{ "task":"Placemnt Carré 1","command":"goto-astar#1680;2350","position":{"x": 1680,"y": 2350,"theta": -0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1690;2350","position":{"x": 1690,"y": 2350,"theta": 0.0}},{ "task":"Placemnt Carré 1","command":"goto-astar#1710;2330","position":{"x": 1710,"y": 2330,"theta": -0.7853981633974483}},{ "task":"Placemnt Carré 1","command":"goto-astar#1750;2330","position":{"x": 1750,"y": 2330,"theta": 0.0}},{ "task":"Alignement Carré 1","command":"face#1750;0","position":{"x": 1750,"y": 2330,"theta": -1.5707963267948966}},{ "task":"Carré 1","command":"action#8","position":{"x": 1750,"y": 2330,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 2","command":"goto#1750;2150","position":{"x": 1750,"y": 2150,"theta": -1.5707963267948966}},{ "task":"Carré 2","command":"action#8","position":{"x": 1750,"y": 2150,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 3","command":"goto#1750;1960","position":{"x": 1750,"y": 1960,"theta": -1.5707963267948966}},{ "task":"Carré 3","command":"action#8","position":{"x": 1750,"y": 1960,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 4","command":"goto#1750;1780","position":{"x": 1750,"y": 1780,"theta": -1.5707963267948966}},{ "task":"Carré 4","command":"action#8","position":{"x": 1750,"y": 1780,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 5","command":"goto#1750;1590","position":{"x": 1750,"y": 1590,"theta": -1.5707963267948966}},{ "task":"Carré 5","command":"action#8","position":{"x": 1750,"y": 1590,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 6","command":"goto#1750;1410","position":{"x": 1750,"y": 1410,"theta": -1.5707963267948966}},{ "task":"Carré 6","command":"action#8","position":{"x": 1750,"y": 1410,"theta": -1.5707963267948966}},{ "task":"Placemnt Carré 7","command":"goto#1750;1220","position":{"x": 1750,"y": 1220,"theta": -1.5707963267948966}},{ "task":"Carré 7","command":"action#8","position":{"x": 1750,"y": 1220,"theta": -1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1750;1220","position":{"x": 1750,"y": 1220,"theta": -1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1720;1250","position":{"x": 1720,"y": 1250,"theta": 2.356194490192345}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1720;1530","position":{"x": 1720,"y": 1530,"theta": 1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1600;1650","position":{"x": 1600,"y": 1650,"theta": 2.356194490192345}},{ "task":"Mise en place rangement zone de fouille","command":"goto#1600;2000","position":{"x": 1600,"y": 2000,"theta": 1.5707963267948966}}]
;var strategySmall0 = [{ "task":"Position de départ","command":"start","position":{"x": 900,"y": 240,"theta": 0.0}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 901,"y": 240,"theta": 0.0}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#900;240","position":{"x": 900,"y": 240,"theta": 3.141592653589793}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#1160;500","position":{"x": 1160,"y": 500,"theta": 0.7853981633974483}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#1600;500","position":{"x": 1600,"y": 500,"theta": 0.0}},{ "task":"Placement chantier récupération statuette","command":"goto#1720;420","position":{"x": 1720,"y": 420,"theta": -0.5880026035475675}},{ "task":"Alignement récupération statuette","command":"face#1800;342","position":{"x": 1720,"y": 420,"theta": -0.7727406115633964}},{ "task":"Récupération statuette","command":"action#9","position":{"x": 1720,"y": 420,"theta": -0.7727406115633964}},{ "task":"Manoeuvre libération réplique","command":"go#-300","position":{"x": 1506,"y": 629,"theta": -0.7727406115633964}},{ "task":"Placement chantier libération réplique","command":"goto#1500;450","position":{"x": 1500,"y": 450,"theta": 4.6788819726038255}},{ "task":"Placement chantier libération réplique","command":"goto#1640;280","position":{"x": 1640,"y": 280,"theta": -0.8818719385800352}},{ "task":"Alignement libération réplique","command":"face#1930;0","position":{"x": 1640,"y": 280,"theta": -0.7678561033400458}},{ "task":"Placement final libération réplique","command":"go#40","position":{"x": 1668,"y": 253,"theta": -0.7678561033400458}},{ "task":"Libération réplique","command":"action#10","position":{"x": 1668,"y": 253,"theta": -0.7678561033400458}},{ "task":"Libération chantier","command":"go#-200","position":{"x": 1525,"y": 391,"theta": -0.7678561033400458}},{ "task":"Déplacement exposition","command":"goto-astar#1520;390","position":{"x": 1520,"y": 390,"theta": 3.338988213439674}},{ "task":"Déplacement exposition","command":"goto-astar#1750;620","position":{"x": 1750,"y": 620,"theta": 0.7853981633974483}},{ "task":"Déplacement exposition","command":"goto-astar#1750;1340","position":{"x": 1750,"y": 1340,"theta": 1.5707963267948966}},{ "task":"Déplacement exposition","command":"goto#1000;1340","position":{"x": 1000,"y": 1340,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#1000;1340","position":{"x": 1000,"y": 1340,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#810;1150","position":{"x": 810,"y": 1150,"theta": 3.9269908169872414}},{ "task":"Déplacement exposition","command":"goto-astar#420;1150","position":{"x": 420,"y": 1150,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#270;1000","position":{"x": 270,"y": 1000,"theta": 3.9269908169872414}},{ "task":"Déplacement exposition","command":"goto-astar#270;310","position":{"x": 270,"y": 310,"theta": -1.5707963267948966}},{ "task":"Placement exposition","command":"goto#90;315","position":{"x": 90,"y": 315,"theta": 3.113822016996372}},{ "task":"Alignement exposition","command":"face#0;315","position":{"x": 90,"y": 315,"theta": 3.141592653589793}},{ "task":"Collage statuette","command":"go#10","position":{"x": 80,"y": 315,"theta": 3.141592653589793}},{ "task":"Dépose statuette","command":"action#9","position":{"x": 80,"y": 315,"theta": 3.141592653589793}},{ "task":"Sortie exposition","command":"go#-190","position":{"x": 270,"y": 315,"theta": 3.141592653589793}},{ "task":"Position echantillon bleu","command":"goto-astar#270;310","position":{"x": 270,"y": 310,"theta": -1.5707963267948966}},{ "task":"Position echantillon bleu","command":"goto-astar#300;340","position":{"x": 300,"y": 340,"theta": 0.7853981633974483}},{ "task":"Position echantillon bleu","command":"goto-astar#300;840","position":{"x": 300,"y": 840,"theta": 1.5707963267948966}},{ "task":"Position echantillon bleu","command":"goto#400;840","position":{"x": 400,"y": 840,"theta": 0.0}},{ "task":"Récupération bleu","command":"action#11","position":{"x": 400,"y": 840,"theta": 0.0}},{ "task":"Stockage bleu","command":"action#12","position":{"x": 400,"y": 840,"theta": 0.0}},{ "task":"Suppression zone bleu","command":"delete-zone#0_SampleBlue","position":{"x": 400,"y": 840,"theta": 0.0}},{ "task":"Position echantillon vert","command":"goto#400;770","position":{"x": 400,"y": 770,"theta": -1.5707963267948966}},{ "task":"Position echantillon vert","command":"goto#520;770","position":{"x": 520,"y": 770,"theta": 0.0}},{ "task":"Récupération vert","command":"action#11","position":{"x": 520,"y": 770,"theta": 0.0}},{ "task":"Switch vert","command":"action#14","position":{"x": 520,"y": 770,"theta": 0.0}},{ "task":"Suppression zone vert","command":"delete-zone#0_SampleGreen","position":{"x": 520,"y": 770,"theta": 0.0}},{ "task":"Position echantillon rouge","command":"goto#520;840","position":{"x": 520,"y": 840,"theta": 1.5707963267948966}},{ "task":"Position echantillon rouge","command":"goto#640;840","position":{"x": 640,"y": 840,"theta": 0.0}},{ "task":"Récupération rouge","command":"action#11","position":{"x": 640,"y": 840,"theta": 0.0}},{ "task":"Suppression zone rouge","command":"delete-zone#0_SampleRed","position":{"x": 640,"y": 840,"theta": 0.0}},{ "task":"Position dépose vert","command":"goto-astar#640;840","position":{"x": 640,"y": 840,"theta": 0.0}},{ "task":"Position dépose vert","command":"goto-astar#510;710","position":{"x": 510,"y": 710,"theta": 3.9269908169872414}},{ "task":"Position dépose vert","command":"goto-astar#300;710","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Position dépose vert","command":"face#0;710","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Depose vert","command":"action#15","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Mise en place dépose vert","command":"go#100","position":{"x": 200,"y": 710,"theta": 3.141592653589793}},{ "task":"Lacher vert","command":"action#16","position":{"x": 200,"y": 710,"theta": 3.141592653589793}},{ "task":"Sortie dépose vert","command":"go#-100","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Switch pour rouge","command":"action#14","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Position dépose Rouge","command":"goto-astar#300;710","position":{"x": 300,"y": 710,"theta": 3.141592653589793}},{ "task":"Position dépose Rouge","command":"goto-astar#300;950","position":{"x": 300,"y": 950,"theta": 1.5707963267948966}},{ "task":"Position dépose Rouge","command":"face#0;950","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Depose Rouge","command":"action#15","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Mise en place dépose Rouge","command":"go#100","position":{"x": 200,"y": 950,"theta": 3.141592653589793}},{ "task":"Lacher Rouge","command":"action#16","position":{"x": 200,"y": 950,"theta": 3.141592653589793}},{ "task":"Sortie dépose Rouge","command":"go#-100","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Switch pour bleu","command":"action#13","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Switch pour bleu","command":"action#14","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Position dépose Bleu","command":"goto-astar#300;950","position":{"x": 300,"y": 950,"theta": 3.141592653589793}},{ "task":"Position dépose Bleu","command":"goto-astar#300;470","position":{"x": 300,"y": 470,"theta": -1.5707963267948966}},{ "task":"Position dépose Bleu","command":"face#0;470","position":{"x": 300,"y": 470,"theta": 3.141592653589793}},{ "task":"Depose Bleu","command":"action#15","position":{"x": 300,"y": 470,"theta": 3.141592653589793}},{ "task":"Mise en place dépose Bleu","command":"go#100","position":{"x": 200,"y": 470,"theta": 3.141592653589793}},{ "task":"Lacher Bleu","command":"action#16","position":{"x": 200,"y": 470,"theta": 3.141592653589793}},{ "task":"Sortie dépose Bleu","command":"go#-100","position":{"x": 300,"y": 470,"theta": 3.141592653589793}},{ "task":"On range tout","command":"action#1","position":{"x": 300,"y": 470,"theta": 3.141592653589793}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#300;470","position":{"x": 300,"y": 470,"theta": 3.141592653589793}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1020;1190","position":{"x": 1020,"y": 1190,"theta": 0.7853981633974483}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1020;1290","position":{"x": 1020,"y": 1290,"theta": 1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1080;1350","position":{"x": 1080,"y": 1350,"theta": 0.7853981633974483}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1150;1350","position":{"x": 1150,"y": 1350,"theta": 0.0}},{ "task":"Mise en place rangement zone de fouille","command":"goto#1150;1000","position":{"x": 1150,"y": 1000,"theta": -1.5707963267948966}}]
;var strategySmall3000 = [{ "task":"Position de départ","command":"start","position":{"x": 900,"y": 2760,"theta": 0.0}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 901,"y": 2760,"theta": 0.0}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#900;2760","position":{"x": 900,"y": 2760,"theta": 3.141592653589793}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#1060;2600","position":{"x": 1060,"y": 2600,"theta": -0.7853981633974483}},{ "task":"Placement chantier récupération statuette","command":"goto-astar#1500;2600","position":{"x": 1500,"y": 2600,"theta": 0.0}},{ "task":"Placement chantier récupération statuette","command":"goto#1580;2730","position":{"x": 1580,"y": 2730,"theta": 1.0191413442663497}},{ "task":"Alignement récupération statuette","command":"face#1840;3000","position":{"x": 1580,"y": 2730,"theta": 0.8042638494191191}},{ "task":"Récupération statuette","command":"action#9","position":{"x": 1580,"y": 2730,"theta": 0.8042638494191191}},{ "task":"Manoeuvre libération réplique","command":"go#-300","position":{"x": 1372,"y": 2514,"theta": 0.8042638494191191}},{ "task":"Placement chantier libération réplique","command":"goto#1500;2450","position":{"x": 1500,"y": 2450,"theta": -0.4636476090008061}},{ "task":"Placement chantier libération réplique","command":"goto#1720;2620","position":{"x": 1720,"y": 2620,"theta": 0.65788860518221}},{ "task":"Alignement libération réplique","command":"face#2000;2935","position":{"x": 1720,"y": 2620,"theta": 0.844153986113171}},{ "task":"Placement final libération réplique","command":"go#40","position":{"x": 1746,"y": 2649,"theta": 0.844153986113171}},{ "task":"Libération réplique","command":"action#10","position":{"x": 1746,"y": 2649,"theta": 0.844153986113171}},{ "task":"Libération chantier","command":"go#-200","position":{"x": 1614,"y": 2500,"theta": 0.844153986113171}},{ "task":"Déplacement exposition","command":"goto-astar#1610;2500","position":{"x": 1610,"y": 2500,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#1750;2360","position":{"x": 1750,"y": 2360,"theta": -0.7853981633974483}},{ "task":"Déplacement exposition","command":"goto-astar#1750;1660","position":{"x": 1750,"y": 1660,"theta": -1.5707963267948966}},{ "task":"Déplacement exposition","command":"goto#1000;1660","position":{"x": 1000,"y": 1660,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#1000;1660","position":{"x": 1000,"y": 1660,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#820;1840","position":{"x": 820,"y": 1840,"theta": 2.356194490192345}},{ "task":"Déplacement exposition","command":"goto-astar#410;1840","position":{"x": 410,"y": 1840,"theta": 3.141592653589793}},{ "task":"Déplacement exposition","command":"goto-astar#270;1980","position":{"x": 270,"y": 1980,"theta": 2.356194490192345}},{ "task":"Déplacement exposition","command":"goto-astar#270;2530","position":{"x": 270,"y": 2530,"theta": 1.5707963267948966}},{ "task":"Placement exposition","command":"goto#90;2535","position":{"x": 90,"y": 2535,"theta": 3.113822016996372}},{ "task":"Alignement exposition","command":"face#0;2535","position":{"x": 90,"y": 2535,"theta": 3.141592653589793}},{ "task":"Collage statuette","command":"go#10","position":{"x": 80,"y": 2535,"theta": 3.141592653589793}},{ "task":"Dépose statuette","command":"action#9","position":{"x": 80,"y": 2535,"theta": 3.141592653589793}},{ "task":"Sortie exposition","command":"go#-190","position":{"x": 270,"y": 2535,"theta": 3.141592653589793}},{ "task":"Position echantillon bleu","command":"goto-astar#270;2530","position":{"x": 270,"y": 2530,"theta": -1.5707963267948966}},{ "task":"Position echantillon bleu","command":"goto-astar#300;2500","position":{"x": 300,"y": 2500,"theta": -0.7853981633974483}},{ "task":"Position echantillon bleu","command":"goto-astar#300;2040","position":{"x": 300,"y": 2040,"theta": -1.5707963267948966}},{ "task":"Position echantillon bleu","command":"goto#400;2040","position":{"x": 400,"y": 2040,"theta": 0.0}},{ "task":"Récupération bleu","command":"action#11","position":{"x": 400,"y": 2040,"theta": 0.0}},{ "task":"Stockage bleu","command":"action#12","position":{"x": 400,"y": 2040,"theta": 0.0}},{ "task":"Suppression zone bleu","command":"delete-zone#3000_SampleBlue","position":{"x": 400,"y": 2040,"theta": 0.0}},{ "task":"Position echantillon vert","command":"goto#400;2110","position":{"x": 400,"y": 2110,"theta": 1.5707963267948966}},{ "task":"Position echantillon vert","command":"goto#520;2110","position":{"x": 520,"y": 2110,"theta": 0.0}},{ "task":"Récupération vert","command":"action#11","position":{"x": 520,"y": 2110,"theta": 0.0}},{ "task":"Switch vert","command":"action#14","position":{"x": 520,"y": 2110,"theta": 0.0}},{ "task":"Suppression zone vert","command":"delete-zone#3000_SampleGreen","position":{"x": 520,"y": 2110,"theta": 0.0}},{ "task":"Position echantillon rouge","command":"goto#520;2040","position":{"x": 520,"y": 2040,"theta": -1.5707963267948966}},{ "task":"Position echantillon rouge","command":"goto#640;2040","position":{"x": 640,"y": 2040,"theta": 0.0}},{ "task":"Récupération rouge","command":"action#11","position":{"x": 640,"y": 2040,"theta": 0.0}},{ "task":"Suppression zone rouge","command":"delete-zone#3000_SampleRed","position":{"x": 640,"y": 2040,"theta": 0.0}},{ "task":"Position dépose vert","command":"goto-astar#640;2040","position":{"x": 640,"y": 2040,"theta": 0.0}},{ "task":"Position dépose vert","command":"goto-astar#390;2290","position":{"x": 390,"y": 2290,"theta": 2.356194490192345}},{ "task":"Position dépose vert","command":"goto-astar#300;2290","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Position dépose vert","command":"face#0;2290","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Depose vert","command":"action#15","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Mise en place dépose vert","command":"go#100","position":{"x": 200,"y": 2290,"theta": 3.141592653589793}},{ "task":"Lacher vert","command":"action#16","position":{"x": 200,"y": 2290,"theta": 3.141592653589793}},{ "task":"Sortie dépose vert","command":"go#-100","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Switch pour rouge","command":"action#14","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Position dépose Rouge","command":"goto-astar#300;2290","position":{"x": 300,"y": 2290,"theta": 3.141592653589793}},{ "task":"Position dépose Rouge","command":"goto-astar#300;2050","position":{"x": 300,"y": 2050,"theta": -1.5707963267948966}},{ "task":"Position dépose Rouge","command":"face#0;2050","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Depose Rouge","command":"action#15","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Mise en place dépose Rouge","command":"go#100","position":{"x": 200,"y": 2050,"theta": 3.141592653589793}},{ "task":"Lacher Rouge","command":"action#16","position":{"x": 200,"y": 2050,"theta": 3.141592653589793}},{ "task":"Sortie dépose Rouge","command":"go#-100","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Switch pour bleu","command":"action#13","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Switch pour bleu","command":"action#14","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Position dépose Bleu","command":"goto-astar#300;2050","position":{"x": 300,"y": 2050,"theta": 3.141592653589793}},{ "task":"Position dépose Bleu","command":"goto-astar#300;2530","position":{"x": 300,"y": 2530,"theta": 1.5707963267948966}},{ "task":"Position dépose Bleu","command":"face#0;2530","position":{"x": 300,"y": 2530,"theta": 3.141592653589793}},{ "task":"Depose Bleu","command":"action#15","position":{"x": 300,"y": 2530,"theta": 3.141592653589793}},{ "task":"Mise en place dépose Bleu","command":"go#100","position":{"x": 200,"y": 2530,"theta": 3.141592653589793}},{ "task":"Lacher Bleu","command":"action#16","position":{"x": 200,"y": 2530,"theta": 3.141592653589793}},{ "task":"Sortie dépose Bleu","command":"go#-100","position":{"x": 300,"y": 2530,"theta": 3.141592653589793}},{ "task":"On range tout","command":"action#1","position":{"x": 300,"y": 2530,"theta": 3.141592653589793}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#300;2530","position":{"x": 300,"y": 2530,"theta": 3.141592653589793}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1020;1810","position":{"x": 1020,"y": 1810,"theta": -0.7853981633974483}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1020;1730","position":{"x": 1020,"y": 1730,"theta": -1.5707963267948966}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1100;1650","position":{"x": 1100,"y": 1650,"theta": -0.7853981633974483}},{ "task":"Mise en place rangement zone de fouille","command":"goto-astar#1150;1650","position":{"x": 1150,"y": 1650,"theta": 0.0}},{ "task":"Mise en place rangement zone de fouille","command":"goto#1150;2000","position":{"x": 1150,"y": 2000,"theta": 1.5707963267948966}}]
;
