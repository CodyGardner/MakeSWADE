package com.example.makeswade;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;

import com.example.makeswade.ui.main.SectionsPagerAdapter;
import com.example.makeswade.Character;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CreateCharacterActivity extends AppCompatActivity {
    private Character newCharacter;
    private int skillPtsRemaining;
    private int attrPtsRemaining;
    private int hindPtsUsable;
    private int hindPtsRemaining;
    private CreateCharacterSkillFragment skillFrag;
    private CreateCharacterAttributeFragment attrFrag;
    private CreateCharacterOtherFragment otherFrag;
    private CreateCharacterInfoFragment infoFrag;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = new DataManager(getApplicationContext());
        hindPtsRemaining = 0;
        hindPtsUsable = 0;
        attrPtsRemaining = 5;
        skillPtsRemaining = 12;

        try { // Create the character here so it's available when the fragments are created
            newCharacter = readBaseCharacter();
            Log.i("info", "New Base Character Created");
        } catch (IOException | XmlPullParserException e) {
            // } catch (Exception e) {
            Log.i("debug", e.getMessage());
        }

        setContentView(R.layout.activity_create_character);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private Character readBaseCharacter() throws XmlPullParserException, IOException {
        Character character = new Character();
        Attribute current_attr = null;
        Skill current_skill = null;
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(getResources().openRawResource(R.raw.base_config), null);
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            Log.i("info", "Found " + tagName + " tag.");
            switch (event) {
                case XmlPullParser.START_TAG:
                    if (tagName.equals("character")) {
                        character.setLevel(Integer.parseInt(parser.getAttributeValue(null,"level")));
                        Log.i("info", "Got character level " + String.valueOf(character.getLevel()));
                    } else if (tagName.equals("attribute")) {
                        current_attr = new Attribute(parser.getAttributeValue(null, "name"),
                                parser.getAttributeValue(null, "abv"));
                        current_attr.setLevel(Integer.parseInt(parser.getAttributeValue(null, "default")));
                        Log.i("info", "Adding attribute " + current_attr.getName() + " to new character");
                        character.addAttribute(current_attr);
                    } else if (tagName.equals("skill")) {
                        current_skill = new Skill(parser.getAttributeValue(null, "name"),
                                current_attr);
                        current_skill.setLevel(Integer.parseInt(parser.getAttributeValue(null, "default")));
                        Log.i("info", "Adding skill " + current_skill.getName() + " to new character");
                        character.addSkill(current_skill);
                    }
                    break;
                default:
                    Log.i("info", "Read basic character found event type " + Integer.toString(event));
                    break;
            }
            event = parser.next();
        }
        return character;
    }

    public Character getNewCharacter() {
        return newCharacter;
    }

    public Drawable dieTypeForLevel(int level) {
        switch (level) {
            case 1:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.d4, null);
            case 2:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.d6, null);
            case 3:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.d8, null);
            case 4:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.d10, null);
            case 5:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.d12, null);
            default:
                return ResourcesCompat.getDrawable(getResources(), R.drawable.untrained, null);
        }
    }

    public int increaseAttr(int position) {
        // TODO
        Attribute attr = newCharacter.getAttrs().get(position);
        int lvl = attr.getLevel() + 1;
        if(attrPtsRemaining > 0) {
            attrPtsRemaining -= 1;
            attrFrag.updateAttrPointsRemaining(attrPtsRemaining);
        } else if (hindPtsRemaining > 1) {
            hindPtsRemaining -= 2;
            skillFrag.updateHindPointsRemaining(hindPtsRemaining);
            attrFrag.updateHindPointsRemaining(hindPtsRemaining);
        } else {
            return -1;
        }
        attr.setLevel(lvl);
        return lvl;
    }

    public int decreaseAttr(int position) {
        // TODO
        Attribute attr = newCharacter.getAttrs().get(position);
        int lvl = attr.getLevel() - 1;
        attr.setLevel(max(1, lvl));
        recalculatePointsRemaining();
        attrFrag.updateAttrPointsRemaining(attrPtsRemaining);
        skillFrag.updateHindPointsRemaining(hindPtsRemaining);
        attrFrag.updateHindPointsRemaining(hindPtsRemaining);
        return lvl;
    }

    public int increaseSkill(int position) {
        // TODO
        Skill skill = newCharacter.getSkills().get(position);
        int lvl = skill.getLevel() + 1;
        int cost = skill.getLevel() + 1 > skill.getAttr().getLevel() ? 2 : 1;
        if (cost > (skillPtsRemaining + hindPtsRemaining))
            return -1;
        skill.setLevel(lvl);
        if (cost > skillPtsRemaining) {
            cost -= skillPtsRemaining;
            skillPtsRemaining = 0;
            hindPtsRemaining -= cost;
        } else skillPtsRemaining -= cost;
        skillFrag.updateSkillPointsRemaining(skillPtsRemaining);
        skillFrag.updateHindPointsRemaining(hindPtsRemaining);
        attrFrag.updateHindPointsRemaining(hindPtsRemaining);
        return lvl;
    }

    public int decreaseSkill(int position) {
        Skill skill = newCharacter.getSkills().get(position);
        int lvl = skill.getLevel() - 1;
        skill.setLevel(max(0, lvl));
        recalculatePointsRemaining();
        skillFrag.updateSkillPointsRemaining(skillPtsRemaining);
        skillFrag.updateHindPointsRemaining(hindPtsRemaining);
        attrFrag.updateHindPointsRemaining(hindPtsRemaining);
        return lvl;
    }

    public boolean addEdge(Edge edge) {
        if (newCharacter.getEdges().isEmpty()) {
            newCharacter.getEdges().add(edge);
            otherFrag.notifyEdgeChange();
            return true;
        }
        else if ((hindPtsRemaining > 1) && newCharacter.getEdges().add(edge)) {
            hindPtsRemaining -= 2;
            skillFrag.updateHindPointsRemaining(hindPtsRemaining);
            attrFrag.updateHindPointsRemaining(hindPtsRemaining);
            otherFrag.notifyEdgeChange();
            return true;
        }
        else return false;
    }

    public boolean addHindrance(Hinderance hindrance) {
        if (!newCharacter.getHinderances().add(hindrance))
            return false;
        if (hindPtsUsable < 4) {
            // Major hindrances worth 2 points, minors worth 1 point
            int ptsToAdd = hindrance.getSeverity() ? 2 : 1;
            hindPtsUsable += ptsToAdd;
            hindPtsUsable = min(4, hindPtsUsable);
            recalculatePointsRemaining();
            skillFrag.updateHindPointsRemaining(hindPtsRemaining);
            attrFrag.updateHindPointsRemaining(hindPtsRemaining);
        }
        otherFrag.notifyHindChange();
        return true;
    }

    private void recalculatePointsRemaining() {
        final int initialSkillpts = 12;
        final int initialAttrPts = 5;
        int hindPtsUsed = 0;
        int temp = 0;

        for (int i=0; i<newCharacter.getAttrs().size(); ++i) {
            temp += newCharacter.getAttrs().get(i).getLevel();
        }
        if (temp >= (5 + initialAttrPts)) {
            hindPtsUsed = 2 * ((temp - 5) - initialAttrPts);
            attrPtsRemaining = 0;
        } else {
            attrPtsRemaining = (5 + initialAttrPts) - temp;
        }
        temp = 0;
        for (int i=0; i<newCharacter.getSkills().size(); ++i) {
            temp += newCharacter.getSkills().get(i).getLevel();
            if (newCharacter.getSkills().get(i).getLevel() >
                newCharacter.getSkills().get(i).getAttr().getLevel()) {
                temp += 1;
            }
        }
        if (temp >= (5 + initialSkillpts)) {
            hindPtsUsed += (5 + initialSkillpts) - temp;
            skillPtsRemaining = 0;
        } else {
            skillPtsRemaining = (5 + initialSkillpts) - temp;
        }

        hindPtsUsed += max(0, (2 * newCharacter.getEdges().size()) - 2);
        hindPtsRemaining = hindPtsUsable - hindPtsUsed;
    }

    public int getSkillPtsRemaining() { return skillPtsRemaining; }
    public int getAttrPtsRemaining() { return attrPtsRemaining; }
    public int getHindPtsRemaining() { return hindPtsRemaining; }
    public void setSkillFrag(CreateCharacterSkillFragment skillFrag) { this.skillFrag = skillFrag; }
    public void setAttrFrag(CreateCharacterAttributeFragment attrFrag) { this.attrFrag = attrFrag; }
    public void setOtherFrag(CreateCharacterOtherFragment otherFrag) { this.otherFrag = otherFrag; }
    public void setInfoFrag(CreateCharacterInfoFragment infoFrag) { this.infoFrag = infoFrag; }

    public void saveCharacter() {
        Log.i("info", "Save button clicked");
        newCharacter.setId((int) dataManager.insertCharacter(newCharacter));
        for (int i=0; i<newCharacter.getAttrs().size(); ++i) {
            Attribute attr = newCharacter.getAttrs().get(i);
            attr.setId((int) dataManager.insertAttr(attr, newCharacter));
        }
        for (int i=0; i<newCharacter.getSkills().size(); ++i) {
            Skill skill = newCharacter.getSkills().get(i);
            skill.setId((int) dataManager.insertSkill(skill));
        }
        for (int i=0; i<newCharacter.getHinderances().size(); ++i) {
            Hinderance hinderance = newCharacter.getHinderances().get(i);
            hinderance.setId((int) dataManager.insertHinderance(hinderance, newCharacter));
        }
        for (int i=0; i<newCharacter.getEdges().size(); ++i) {
            Edge edge = newCharacter.getEdges().get(i);
            edge.setId((int) dataManager.insertEdge(edge, newCharacter));
        }
    }

    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getThumbnailFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri image = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if(bitmap == null) {
                    Log.i("info", "bitmap is null");
                }
                newCharacter.setThumbnail(bitmap);
                infoFrag.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.i("info", e.getMessage());
            }
        }
    }
}