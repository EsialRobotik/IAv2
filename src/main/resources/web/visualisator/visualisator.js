var stage;

var bigPrincess;
var pmiPrincess;

var stratSimulator;
var stratLog;
var stratIndex = 0;
var timestampLog;
var deltaTimestampLog;

var stratPmiSimulator;
var stratPmiLog;
var stratPmiIndex = 0;
var timestampPmiLog;
var deltaTimestampPmiLog;

var rotationTime = 100;
var moveTime = 600;

/**
 * Initialisation des robots à animer
 */
function init() {
    stage = new createjs.Stage("canvas");

    bigPrincess = new createjs.Bitmap('./bigPrincess.png');
    bigPrincess.x = 800;
    bigPrincess.y = 800;
    // Décallage du point d'animation au centre du robot
    bigPrincess.regX = 150;
    bigPrincess.regY = 100;
    bigPrincess.alpha = 0.7;
    stage.addChild(bigPrincess);

    pmiPrincess = new createjs.Bitmap('./pmiPrincess.png');
    pmiPrincess.x = 1200;
    pmiPrincess.y = 800;
    // Décallage du point d'animation au centre du robot
    pmiPrincess.regX = 150;
    pmiPrincess.regY = 75;
    pmiPrincess.alpha = 0.7;
    stage.addChild(pmiPrincess);

    stage.update();

    createjs.Ticker.setFPS(60);
    createjs.Ticker.addEventListener("tick", stage);
    initRobot();

    var inputs = document.querySelectorAll('.inputfile');
    Array.prototype.forEach.call(inputs, function (input) {
        var label = input.nextElementSibling,
            labelVal = label.innerHTML;

        input.addEventListener('change', function (e) {
            var fileName = '';
            if (this.files && this.files.length > 1)
                fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
            else
                fileName = e.target.value.split('\\').pop();

            if (fileName)
                label.querySelector('span').innerHTML = fileName;
            else
                label.innerHTML = labelVal;
        });
    });
}

/**
 * Mise en position de départ des robots
 */
function initRobot() {
    createjs.Tween.get(bigPrincess)
        .to({rotation: radiansToDegrees(Math.PI - Math.PI / 2)}, rotationTime, createjs.Ease.getPowInOut(4))
        .to({x: 200, y: 750}, moveTime, createjs.Ease.getPowInOut(4));

    createjs.Tween.get(pmiPrincess)
        .to({rotation: radiansToDegrees(0)}, rotationTime, createjs.Ease.getPowInOut(4))
        .to({x: 240, y: 1020}, moveTime, createjs.Ease.getPowInOut(4));
}

/**
 * Déplacement robot principal
 * @param x Position en X
 * @param y Position en Y
 * @param rotation Angle
 * @param speed Temps d'exécution
 */
function moveRobot(x, y, rotation, speed) {
    var shape = new createjs.Shape();
    shape.graphics
        .setStrokeStyle(3)
        .beginStroke('rgba(255,20,147,1)')
        .moveTo(bigPrincess.x, bigPrincess.y)
        .lineTo(y, x);
    stage.addChild(shape);
    stage.update();

    var tRotation = rotationTime;
    var tMove = moveTime;
    if (speed) {
        tRotation = speed * 1/3;
        tMove = speed * 2/3;
    }

    createjs.Tween.get(bigPrincess)
        .to({rotation: radiansToDegrees(Math.PI - rotation)}, tRotation, createjs.Ease.getPowInOut(4))
        .to({x: y, y: x}, tMove, createjs.Ease.getPowInOut(4));
}

/**
 * Déplacement robot PMI
 * @param x Position en X
 * @param y Position en Y
 * @param rotation Angle
 * @param speed Temps d'exécution
 */
function movePmi(x, y, rotation, speed) {
    var shape = new createjs.Shape();
    shape.graphics
        .setStrokeStyle(3)
        .beginStroke('rgb(255,105,180,1)')
        .moveTo(pmiPrincess.x, pmiPrincess.y)
        .lineTo(y, x);
    stage.addChild(shape);
    stage.update();

    var tRotation = rotationTime;
    var tMove = moveTime;
    if (speed) {
        tRotation = speed * 1/3;
        tMove = speed * 2/3;
    }

    createjs.Tween.get(pmiPrincess)
        .to({rotation: radiansToDegrees(Math.PI - rotation)}, tRotation, createjs.Ease.getPowInOut(4))
        .to({x: y, y: x}, tMove, createjs.Ease.getPowInOut(4));
}

/**
 * Convertion des angles en degrés
 * @param radians
 * @returns {number}
 */
function radiansToDegrees(radians) {
    var pi = Math.PI;
    return radians * (180 / pi);
}

/**
 * Chargement du fichier table.json et affichage des zones interdites
 * @returns {boolean}
 */
function loadTable() {
    var file = document.getElementById('table');
    if (file && file.files && file.files.length) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var jsonTable = JSON.parse(e.target.result);

            // Zones interdites fixes
            jsonTable.zonesInterdites.forEach(zone => {
                displayZone(zone, jsonTable, 'rgba(255,0,0,0.6)', 'rgba(200,0,0,0.4)');
            });

            // Bordure haut
            var shape = new createjs.Shape();
            shape.graphics
                .beginFill('rgba(200,0,0,0.4)')
                .drawRect(0, 0, 3000, jsonTable.marge);
            stage.addChild(shape);

            // Bordure bas
            shape = new createjs.Shape();
            shape.graphics
                .beginFill('rgba(200,0,0,0.4)')
                .drawRect(0, 2000 - jsonTable.marge, 3000, 2000);
            stage.addChild(shape);

            // Bordure gauche
            shape = new createjs.Shape();
            shape.graphics
                .beginFill('rgba(200,0,0,0.4)')
                .drawRect(0, 0, jsonTable.marge, 2000);
            stage.addChild(shape);

            // Bordure droite
            shape = new createjs.Shape();
            shape.graphics
                .beginFill('rgba(200,0,0,0.4)')
                .drawRect(3000 - jsonTable.marge, 0, 3000, 2000);
            stage.addChild(shape);

            // Zones interdites mobiles
            jsonTable.elementsJeu.forEach(zone => {
                displayZone(zone, jsonTable, 'rgba(255,255,0,0.6)', 'rgba(255,165,0,0.4)');
            });
            stage.update();

            deleteZone('start0');
        };
        reader.readAsBinaryString(file.files[0]);

        return true;
    }
}

/**
 * Affichage des zones interdites
 * @param zone
 * @param jsonTable
 * @param colorPrimary
 * @param colorSecondary
 */
function displayZone(zone, jsonTable, colorPrimary, colorSecondary) {
    if (zone.forme == 'polygone') {
        if (zone.points.length === 4) {
            var topLeftCorner = zone.points[0];
            var bottomRightCorner = zone.points[0];
            zone.points.forEach(point => {
                if (topLeftCorner.x >= point.x && topLeftCorner.y >= point.y) {
                    topLeftCorner = point;
                }
                if (bottomRightCorner.x <= point.x && bottomRightCorner.y <= point.y) {
                    bottomRightCorner = point;
                }
            });

            var shape = new createjs.Shape();
            shape.name = zone.id;
            shape.visible = zone.active;
            shape.graphics
                .beginFill(colorPrimary)
                .drawRect(
                    topLeftCorner.y,
                    topLeftCorner.x,
                    bottomRightCorner.y - topLeftCorner.y,
                    bottomRightCorner.x - topLeftCorner.x
                );
            stage.addChild(shape);

            shape = new createjs.Shape();
            shape.name = zone.id + '_margin';
            shape.visible = zone.active;
            shape.graphics
                .beginFill(colorSecondary)
                .drawRect(
                    topLeftCorner.y - jsonTable.marge,
                    topLeftCorner.x - jsonTable.marge,
                    (bottomRightCorner.y - topLeftCorner.y) + jsonTable.marge * 2,
                    (bottomRightCorner.x - topLeftCorner.x) + jsonTable.marge * 2
                );
            stage.addChild(shape);
        } else {
            alert('Je gère pas encore ce cas, have fun : ' + zone.id + '-' + zone.forme + '-' + zone.active);
        }
    } else if (zone.forme == 'cercle') {
        var shape = new createjs.Shape();
        shape.name = zone.id;
        shape.visible = zone.active;
        shape.graphics
            .beginFill(colorPrimary)
            .drawCircle(zone.centre.y, zone.centre.x, zone.rayon);
        stage.addChild(shape);

        shape = new createjs.Shape();
        shape.name = zone.id + '_margin';
        shape.visible = zone.active;
        shape.graphics
            .beginFill(colorSecondary)
            .drawCircle(zone.centre.y, zone.centre.x, zone.rayon + jsonTable.marge);
        stage.addChild(shape);
    }
}

/**
 * Suppression d'une zone interdite
 * @param zoneName
 */
function deleteZone(zoneName) {
    if (stage.getChildByName(zoneName)) {
        stage.getChildByName(zoneName).visible = false;
        stage.getChildByName(zoneName + '_margin').visible = false;
        stage.update();
    }
}

/**
 * Activation d'une zone interdite
 * @param zoneName
 */
function addZone(zoneName) {
    if (stage.getChildByName(zoneName)) {
        stage.getChildByName(zoneName).visible = true;
        stage.getChildByName(zoneName + '_margin').visible = true;
        stage.update();
    }
}

/**
 * Chargement de la start simulateur du robot principal
 * @returns {boolean}
 */
function loadSimulatorStrat() {
    var file = document.getElementById('strat');
    if (file && file.files && file.files.length) {
        var reader = new FileReader();
        reader.onload = function (e) {
            stratSimulator = JSON.parse(e.target.result);
            stratIndex = 0;
        };
        reader.readAsBinaryString(file.files[0]);
        document.getElementById('princessNext').disabled = false;
        document.getElementById('princessAuto').disabled = false;
        return true;
    }
}

/**
 * Chargement de la start simulateur du robot PMI
 * @returns {boolean}
 */
function loadSimulatorStratPmi() {
    var file = document.getElementById('stratPmi');
    if (file && file.files && file.files.length) {
        var reader = new FileReader();
        reader.onload = function (e) {
            stratPmiSimulator = JSON.parse(e.target.result);
            stratPmiIndex = 0;
        };
        reader.readAsBinaryString(file.files[0]);
        document.getElementById('pmiNext').disabled = false;
        document.getElementById('pmiAuto').disabled = false;
        return true;
    }
}

/**
 * Chargement des logs d'un match du robot principal
 * @returns {boolean}
 */
function loadStratLog() {
    var file = document.getElementById('stratLog');
    if (file && file.files && file.files.length) {
        var reader = new FileReader();
        reader.onload = function (e) {
            stratLog = cleanLogFile(e.target.result);
            rotationTime = 50;
            moveTime = 50;
            stratIndex = 0;

            for (var key in stratLog) {
                if (stratLog[key].includes('TRACE: Position :')) {
                    var regexpAsserv = /.+\[Asserv\].+#([-0-9]+);([-0-9]+);([-0-9\.]+).+/;
                    var parseAsserv = regexpAsserv.exec(stratLog[key]);
                    if (parseAsserv != null) {
                        createjs.Tween.get(bigPrincess)
                            .to({rotation: radiansToDegrees(parseAsserv[3])}, rotationTime, createjs.Ease.getPowInOut(4))
                            .to({x: parseAsserv[2], y: parseAsserv[1]}, moveTime, createjs.Ease.getPowInOut(4));
                        console.log('Princess init : ' + parseAsserv[1] + ' - ' + parseAsserv[2] + ' - ' + radiansToDegrees(parseAsserv[3]));
                        break;
                    }
                }
            }
        };
        reader.readAsBinaryString(file.files[0]);
        document.getElementById('princessNext').disabled = false;
        document.getElementById('princessAuto').disabled = false;
        return true;
    }
}

/**
 * Chargement des logs d'un match du robot principal
 * @returns {boolean}
 */
function loadStratLogPmi() {
    var file = document.getElementById('stratPmiLog');
    if (file && file.files && file.files.length) {
        var reader = new FileReader();
        reader.onload = function (e) {
            stratPmiLog = cleanLogFile(e.target.result);
            rotationTime = 50;
            moveTime = 50;
            stratPmiIndex = 0;

            for (var key in stratPmiLog) {
                if (stratPmiLog[key].includes('TRACE: Position :')) {
                    var regexpAsserv = /.+\[Asserv\].+#([-0-9]+);([-0-9]+);([-0-9\.]+).+/;
                    var parseAsserv = regexpAsserv.exec(stratPmiLog[key]);
                    if (parseAsserv != null) {
                        createjs.Tween.get(pmiPrincess)
                            .to({rotation: radiansToDegrees(parseAsserv[3])}, rotationTime, createjs.Ease.getPowInOut(4))
                            .to({x: parseAsserv[2], y: parseAsserv[1]}, moveTime, createjs.Ease.getPowInOut(4));
                        console.log('PMI init : ' + parseAsserv[1] + ' - ' + parseAsserv[2] + ' - ' + radiansToDegrees(parseAsserv[3]));
                        break;
                    }
                }
            }
        };
        reader.readAsBinaryString(file.files[0]);
        document.getElementById('pmiNext').disabled = false;
        document.getElementById('pmiAuto').disabled = false;
        return true;
    }
}

function cleanLogFile(file) {
    var res = [];
    var drop = true;
    var split = file.split('\n');
    for (var key in split) {

        if (drop && split[key].includes('Tirette pull, begin of the match')) {
            drop = false;
        }
        if (!drop && split[key] !== '') {
            res.push(split[key]);
        }
    }
    return res;
}

/**
 * Récupération et application de l'instruction suivante de la Princess
 * @returns {boolean}
 */
function nextInstruction() {
    var startFile = stratSimulator ? stratSimulator : stratLog;
    if (stratIndex >= startFile.length) {
        return true;
    }
    var instruction = startFile[stratIndex];
    stratIndex++;

    if (stratSimulator) {
        playSimulatorInstruction(instruction, 'data', 'princess');
    } else {
        playLogInstruction(instruction, 'data', 'princess');
    }
    return false;
}

/**
 * Récupération et application de l'instruction suivante de la PMI
 * @returns {boolean}
 */
function nextInstructionPmi() {
    var startFile = stratPmiSimulator ? stratPmiSimulator : stratPmiLog;
    if (stratPmiIndex >= startFile.length) {
        return true;
    }
    var instruction = startFile[stratPmiIndex];
    stratPmiIndex++;

    if (stratPmiSimulator) {
        playSimulatorInstruction(instruction, 'dataPmi', 'pmi');
    } else {
        playLogInstruction(instruction, 'dataPmi', 'pmi');
    }
    return false;
}

/**
 * Exécution d'une instruction du simulateur
 * @param instruction
 * @param divId
 * @param who
 */
function playSimulatorInstruction(instruction, divId, who) {
    var regexpZone = /(delete|add)-zone#(.+)/;
    var parseZone = regexpZone.exec(instruction.command);

    var dataDiv = document.getElementById(divId);
    dataDiv.insertAdjacentHTML('beforeend', '<strong>' + instruction.task + '</strong> : ' + instruction.command + '<br>');
    dataDiv.scrollTop = dataDiv.scrollHeight;
    if (who === 'princess') {
        moveRobot(instruction.position.x, instruction.position.y, instruction.position.theta);
    } else {
        movePmi(instruction.position.x, instruction.position.y, instruction.position.theta);
    }
    if (parseZone !== null) {
        if (parseZone[1] === 'delete') {
            deleteZone(parseZone[2]);
        } else {
            addZone(parseZone[2]);
        }
    }
}

function playLogInstruction(instruction, divId, who) {
    var regexpTimestamp = /([0-9]{4}-[0-9]{2}-[0-9]{2}) ([0-9]{2}):([0-9]{2}):([0-9]{2}),([0-9]+) .+/;
    var parseTimestamp = regexpTimestamp.exec(instruction);
    var delta = undefined;
    if (parseTimestamp != null) {
        var date = Date.parse(parseTimestamp[1] + 'T' + parseTimestamp[2] + ':' + parseTimestamp[3] + ':' + parseTimestamp[4] + '.' + parseTimestamp[5] + '+00:00');
        if (who === 'princess') {
            delta = date - timestampLog;
            timestampLog = date;
        } else {
            delta = date - timestampPmiLog;
            timestampPmiLog = date;
        }
    }
    if (who === 'princess') {
        deltaTimestampLog = delta ? delta : 0;
    } else {
        deltaTimestampPmiLog = delta ? delta : 0;
    }

    var regexpAsserv = /.+\[Asserv\].+#([-0-9]+);([-0-9]+);([-0-9\.]+).+/;
    var parseAsserv = regexpAsserv.exec(instruction);
    var regexpInfo = /.+INFO :(.+)/;
    if (parseAsserv != null) {
        if (who === 'princess') {
            moveRobot(parseAsserv[1], parseAsserv[2], parseAsserv[3], delta);
        } else {
            movePmi(parseAsserv[1], parseAsserv[2], parseAsserv[3], delta);
        }
    } else {
        var parseInfo = regexpInfo.exec(instruction);
        if (parseInfo != null) {
            var dataDiv = document.getElementById(divId);
            dataDiv.insertAdjacentHTML('beforeend', parseInfo[1] + '<br>');
            dataDiv.scrollTop = dataDiv.scrollHeight;
        }
    }
}

async function autoPlay() {
    if (!nextInstruction()) {
        await sleep(deltaTimestampLog ? deltaTimestampLog : rotationTime + moveTime + 1);
        autoPlay();
    }
}

async function autoPlayPmi() {
    if (!nextInstructionPmi()) {
        await sleep(deltaTimestampPmiLog ? deltaTimestampPmiLog : rotationTime + moveTime + 1);
        autoPlayPmi();
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function loadFiles() {
    loadTable();
    loadSimulatorStrat();
    loadSimulatorStratPmi();
    loadStratLog();
    loadStratLogPmi();
}

function connectSocket() {
    var socket = null;
    try {
        socket = new WebSocket("ws://192.168.0.104:4269");
    } catch (exception) {
        console.error(exception);
    }

    // Récupération des erreurs.
    // Si la connexion ne s'établie pas,
    // l'erreur sera émise ici.
    socket.onerror = function (error) {
        console.error(error);
    };

    // Lorsque la connexion est établie.
    socket.onopen = function (event) {
        console.log("Connexion établie.");

        // Lorsque la connexion se termine.
        this.onclose = function (event) {
            console.log("Connexion terminé.");
        };

        // Lorsque le serveur envoi un message.
        this.onmessage = function (event) {
            console.log("Message:", event.data);
        };

        this.send("loggerListener");
    };
}
