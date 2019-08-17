<p align="center">
  <a href="" rel="noopener">
 <img src="https://i.imgur.com/yyDQed0.png" alt="Project logo"></a>
</p>
<h3 align="center">Malaria Diagnosis Tool</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)](https://github.com/JamesWRC/MDT_Lite)
[![GitHub Issues](https://img.shields.io/github/issues/jameswrc/MDT_Lite.svg)](https://github.com/JamesWRC/MDT_Lite/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/JamesWRC/MDT_Lite.svg)](https://github.com/JamesWRC/MDT_Lite/pulls)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.md)

</div>

---

<p align="center"> This Malaria Diagnosis Tool (MDT) helps lab scientists diagnose which type of malaria the patient may have depending on the country they have recently come from.

- This is a Lite version which essentaily means its just a Command Line Version of the program. It has all the features of the Graphical User Interface (GUI) version.
- Please only use this version if you DON'T want a GUI and / or are comfortable using a command prompt.
- For a version with a GUI [please go here](https://github.com/JamesWRC/MDT_GUI)

  </p>

## 📝 Table of Contents

- [Why Use This Tool?](#why_use_this_tool)
- [How to install and use](#how_to_use)
- [Images](#images)
- [Known bugs](#known_bugs)
- [Planned features](#planned_features)

# Why Use This Tool <a name = "why_use_this_tool"></a>

If you're a lab scientist and/or work in pathology you would be used to viewing blood slides to assess which type of Malaria the patient may have so the patient can get the right treatment.

Sometimes you may get either a Country Name or Country Code of where the patient is from / has recenlty come from and may take a long time to lookup the country and see which malaria type is more prevalent. You can enter either the country name OR country code and this toll will still display results.

- Fast - Get Malaria types and their likeliness in seconds.
- Easy To Use - Can enter country name OR country code. And if you dont feel like you or the people to use this tool is familier with Command Line Interface (CLI) then there is even an even easier to use [GUI Version Here!](https://github.com/JamesWRC/MDT_GUI)
- Simple - Just type in a countrys name or code and the likeliness of each Malaria type is displayed in a nice orderly fassion.
- Easy to set up

# How to install and use <a name = "how_to_use"></a>

IMPORTANT: You must have Java installed onto the computers you want to use this program on. You can get the latest java [here](https://www.java.com/en/download/).
Please note, you may have to compile the project yourself if your version of java wont run it.
If you have any issues running MDT please open a suport ticket [here](https://github.com/JamesWRC/MDT_Lite/issues)

1. Download the 'MDT_Lite.jar' file above. All versions are found [here](https://github.com/JamesWRC/MDT_Lite/tree/master/releases)
2. Open a command prompt, go to where you saved the 'MDT_Lite.jar' file
3. Run the following command:

```
java -jar MDT_Lite.jar
```

4. Done! Enjoy
   For some basic examples of how to use the tool please see the [images](#images) below.

# Images <a name = "images"></a>

## Note: your verion of MDT_Lite may be different to the file in the images below

<img src="https://i.imgur.com/78FXJtS.png" alt="Project image1" width="1000" height="100">

Easily run the program with the command above.

<hr>

  <img src="https://i.imgur.com/OqGkWyy.png" alt="Project image2" width="800" height="325">

Use either a country code like 'AU' or a country name like 'Australia'. Note: Case does <u>not</u> matter.

<hr>
<img src="https://i.imgur.com/q0k3xYX.png" alt="Project image3" width="800" height="450">

Countries can have notes that show when the country is selected. These notes can easily be changed or modified. (EDITING OR ADDING NOTES TO A COUNTRY IS A PLANNED FEATURE AND CAN NOT BE MODIFIED AT THIS TIME.)

# Known Bugs<a name = "known_bugs"></a>

- None :)

# Planned Features<a name = "planned_features"></a>

- The ability to edit notes for a country.
- Change this readme.md for betetr understand between the GUI version and this CLI version.
